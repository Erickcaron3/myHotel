package net.erickcaron.reservationapplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(name = "reservations")

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comments;

    @Future
    private LocalDateTime reservationFrom;

    @Future
    private LocalDateTime reservationTo;

    private LocalDate createdOn;

    private LocalDate modifiedOn;

    @ElementCollection
    private Set<Long> apartmentIdList = new HashSet<>();

    @Enumerated
    private ReservationStatus reservationStatus;

    @PrePersist
    private void PrePersist() {
        createdOn = LocalDate.now();
        reservationStatus = ReservationStatus.REQUESTED;
    }

    @PreUpdate
    private void PreUpdate() {
        modifiedOn = LocalDate.now();
    }

}
