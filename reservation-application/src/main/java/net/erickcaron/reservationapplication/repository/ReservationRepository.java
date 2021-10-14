package net.erickcaron.reservationapplication.repository;

import net.erickcaron.reservationapplication.model.Reservation;
import net.erickcaron.reservationapplication.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByReservationStatus(ReservationStatus reservationStatus);
}
