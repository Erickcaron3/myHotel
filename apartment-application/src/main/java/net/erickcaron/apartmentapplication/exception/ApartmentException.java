package net.erickcaron.apartmentapplication.exception;

public class  ApartmentException extends RuntimeException{

    private ApartmentError apartmentError;

    public ApartmentException(ApartmentError apartmentError){
        this.apartmentError = apartmentError;
    }


    public ApartmentError getApartmentError() {
        return apartmentError;
    }
}
