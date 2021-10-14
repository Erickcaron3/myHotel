package net.erickcaron.apartmentapplication.exception;

public enum BedError {

    BED_NOT_FOUND("Bed does not exist"),
    INCORRECT_NB_OF_PLACES_INPUTTED("Incorrect numbers of places"),
    INCORRECT_NAME_INPUTTED("Incorrect name"),
    BED_ALREADY_USED("Bed already used"),
    BED_USED_NOT_DELETABLE("Bed currently used so not deletable");

    private String message;

    BedError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
