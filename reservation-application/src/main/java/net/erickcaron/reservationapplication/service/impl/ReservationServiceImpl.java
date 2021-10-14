package net.erickcaron.reservationapplication.service.impl;

import net.erickcaron.reservationapplication.exception.ReservationError;
import net.erickcaron.reservationapplication.exception.ReservationException;
import net.erickcaron.reservationapplication.model.ApartmentDTO;
import net.erickcaron.reservationapplication.model.ApartmentDTOStatus;
import net.erickcaron.reservationapplication.model.Reservation;
import net.erickcaron.reservationapplication.model.ReservationStatus;
import net.erickcaron.reservationapplication.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ApartmentServiceClient apartmentServiceClient;

    @Override
    public List<Reservation> findAll(ReservationStatus reservationStatus) {
        if (reservationStatus != null) {
            return reservationRepository.findAllByReservationStatus(reservationStatus);
        }
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));
    }

    @Override
    public Long create(Reservation reservation) {
        checkReservationInputCorrect(reservation);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    public void update(Long id) {
        Reservation reservationToUpdate = findById(id);
        checkReservationInputCorrect(reservationToUpdate);
        reservationRepository.save(reservationToUpdate);
    }

    @Override
    public void delete(Long id) {
        Reservation reservationToDelete = findById(id);
        reservationRepository.delete(reservationToDelete);
        Set<Long> apartmentIdList = reservationToDelete.getApartmentIdList(); //TODO optimaliser!!!
        for (Long apartmentId : apartmentIdList) {
            cancelApartmentBooking(apartmentId, id);
        }

    }

    @Override
    public void book(Long reservationId, Long apartmentId, String action) {
        if (action == null) {
            throw new ReservationException(ReservationError.UNAUTHORIZED_BOOKING_REQUEST);
        }

        if (action.equals("book")) {
            bookApartment(reservationId, apartmentId);
        }

        if (action.equals("cancel")) {
            cancelApartmentBooking(reservationId, apartmentId);
        }
    }

    private void bookApartment(Long reservationId, Long apartmentId) {
        ApartmentDTO apartById = apartmentServiceClient.findById(apartmentId);
        checkIfApartmentStatusAuthorizeBooking(apartById);

        Reservation reservationById = findById(reservationId);
        reservationById.getApartmentIdList().add(apartById.getId());

        reservationRepository.save(reservationById);
        apartmentServiceClient.updateApartmentStatus(apartmentId, ApartmentDTOStatus.BOOKED);

    }

    private void cancelApartmentBooking(Long reservationId, Long apartmentId) {
        ApartmentDTO apartById = apartmentServiceClient.findById(apartmentId);
        checkIfApartmentIsBooked(apartById);

        Reservation reservationById = findById(reservationId);
        reservationById.getApartmentIdList().remove(apartById);

        reservationRepository.save(reservationById);
        apartmentServiceClient.updateApartmentStatus(apartmentId, ApartmentDTOStatus.FREE);


    }

    private Reservation checkReservationInputCorrect(Reservation reservation) {
        return reservation;
    }

    private void checkIfApartmentStatusAuthorizeBooking(ApartmentDTO apartmentDTO) {
        if (ApartmentDTOStatus.BOOKED.equals(apartmentDTO.getApartmentDTOStatus()) || ApartmentDTOStatus.UNAVAILABLE.equals(apartmentDTO.getApartmentDTOStatus())) {
            throw new ReservationException(ReservationError.APARTMENT_NOT_BOOKABLE);
        }
    }

    private void checkIfApartmentIsBooked(ApartmentDTO apartmentDTO) {
        if (!ApartmentDTOStatus.BOOKED.equals(apartmentDTO.getApartmentDTOStatus())) {
            throw new ReservationException(ReservationError.APARTMENT_NOT_BOOKED);
        }
    }


}
