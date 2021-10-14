package net.erickcaron.customerapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorInfo> handleException(CustomerException customerException){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(CustomerError.CUSTOMER_NOT_EXISTING.equals(customerException.getCustomerError())){
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(customerException.getCustomerError().getMessage()));


    }
}
