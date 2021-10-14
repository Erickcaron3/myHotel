package net.erickcaron.reservationapplication.service.impl;

import net.erickcaron.reservationapplication.model.Reservation;
import net.erickcaron.reservationapplication.model.ReservationStatus;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAll(ReservationStatus reservationStatus);

    Reservation findById(Long id);

    Long create(Reservation reservation);

    void update(Long id);

    void delete(Long id);

    void book(Long reservationId, Long apartmentId, String action);

}
