package net.erickcaron.apartmentapplication.exception;


public class BedException extends RuntimeException {

    private BedError bedError;

    public BedException(BedError bedError) {
        this.bedError = bedError;
    }


    public BedError getBedError() {
        return bedError;
    }
}
