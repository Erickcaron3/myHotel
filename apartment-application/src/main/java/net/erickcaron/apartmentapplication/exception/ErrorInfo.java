package net.erickcaron.apartmentapplication.exception;


public class ErrorInfo {

    private String message;

    public String getMessage() {
        return message;
    }

    public ErrorInfo(String message) {
        this.message = message;
    }
}