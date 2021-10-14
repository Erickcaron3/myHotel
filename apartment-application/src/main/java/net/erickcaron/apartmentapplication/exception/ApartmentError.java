package net.erickcaron.apartmentapplication.exception;


public enum ApartmentError {

    APARTMENT_NOT_FOUND("Apartment does not exist"),
    INCORRECT_NAME_INPUT("Incorrect or already existing name provided"),
    INCORRECT_EQUIPMENTS_INPUT("Incorrect equipements provided"),
    INCORRECT_NB_OF_BEDS("Incorrect number of beds (must be higher than 0"),
    INCORRECT_NB_OF_PERSONS("Incorrect number of persons (must be higher than 0"),
    INCORRECT_QUERY("This query is incorrect, please check keyword"),
    TOLERANCE_NOT_RESPECTED("Number of persons wanted is higher than the one authorised");

    private String message;

    ApartmentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
