package net.erickcaron.apartmentapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApartmentExceptionHandler {

    @ExceptionHandler(ApartmentException.class)
    public ResponseEntity<ErrorInfo> handleException(ApartmentException ae) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ApartmentError.APARTMENT_NOT_FOUND.equals(ae.getApartmentError())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (ApartmentError.INCORRECT_EQUIPMENTS_INPUT.equals(ae.getApartmentError())
                || ApartmentError.INCORRECT_NAME_INPUT.equals(ae.getApartmentError())
                || ApartmentError.INCORRECT_NB_OF_BEDS.equals(ae.getApartmentError())
                || ApartmentError.INCORRECT_NB_OF_PERSONS.equals(ae.getApartmentError())
                || ApartmentError.INCORRECT_QUERY.equals(ae.getApartmentError())
                || ApartmentError.TOLERANCE_NOT_RESPECTED.equals(ae.getApartmentError())) {
            httpStatus = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(ae.getApartmentError().getMessage()));
    }
}
