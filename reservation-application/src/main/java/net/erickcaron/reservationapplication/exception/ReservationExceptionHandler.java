package net.erickcaron.reservationapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorInfo> handleException(ReservationException reservationException) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ReservationError.RESERVATION_NOT_FOUND.equals(reservationException.getReservationError())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (ReservationError.INCORRECT_RESERVATION_DATE.equals(reservationException.getReservationError())) {
            httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(reservationException.getReservationError().getMessage()));
    }
}
