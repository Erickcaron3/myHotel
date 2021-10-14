package net.erickcaron.customerapplication.exception;

public enum CustomerError {

    CUSTOMER_NOT_EXISTING("The customer you're asking for is not existing"),
    INCORRECT_IDENTITY_INPUT("The first name or last name input can not be empty"),
    INCORRECT_EMAIL_INPUT("The email address provided is incorrect"),
    CUSTOMER_DATAS_DELETED("The customer you are asking for have been asking for his datas deletion");
    private String message;

    CustomerError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
