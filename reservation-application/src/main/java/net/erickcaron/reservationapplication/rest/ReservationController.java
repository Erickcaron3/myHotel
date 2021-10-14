package net.erickcaron.reservationapplication.rest;

import net.erickcaron.reservationapplication.exception.ReservationError;
import net.erickcaron.reservationapplication.exception.ReservationException;
import net.erickcaron.reservationapplication.model.Reservation;
import net.erickcaron.reservationapplication.model.ReservationStatus;
import net.erickcaron.reservationapplication.service.impl.ApartmentServiceClient;
import net.erickcaron.reservationapplication.service.impl.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> findAll(@RequestParam(name = "status", required = false)ReservationStatus reservationStatus){
        return reservationService.findAll(reservationStatus);
    }

    @GetMapping("/{id}")
    public Reservation findById(@PathVariable("id") Long id){
        return reservationService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Reservation reservation){
        return reservationService.create(reservation);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id){
        reservationService.update(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        reservationService.delete(id);
    }

    @PatchMapping("/{reservationId}/apartments/{apartmentId}")
    public void book(@PathVariable Long reservationId, @PathVariable Long apartmentId, @RequestParam("action") String action){
        reservationService.book(reservationId, apartmentId, action);
    }



}
