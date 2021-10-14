package net.erickcaron.apartmentapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BedExceptionHandler {

    @ExceptionHandler(value = BedException.class)
    public ResponseEntity<ErrorInfo> handleException(BedException be) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (BedError.BED_NOT_FOUND.equals(be.getBedError())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (BedError.INCORRECT_NAME_INPUTTED.equals(be.getBedError())
                || (BedError.INCORRECT_NB_OF_PLACES_INPUTTED.equals(be.getBedError()))
                || (BedError.BED_ALREADY_USED.equals(be.getBedError()))
                || (BedError.BED_USED_NOT_DELETABLE.equals(be.getBedError()))) {
            httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(be.getBedError().getMessage()));
    }
}
