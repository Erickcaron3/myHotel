package net.erickcaron.reservationapplication.exception;

public enum ReservationError {

    RESERVATION_NOT_FOUND("Reservation not found"),
    INCORRECT_RESERVATION_DATE("The reservation dates must be in the future"),
    APARTMENT_NOT_BOOKABLE("The apartment you're tryng to book is already booked"),
    APARTMENT_NOT_BOOKED("You're trying to cancel the booking of an apartment not booked"),
    UNAUTHORIZED_BOOKING_REQUEST("Unauthorized booking request - should be or book or cancel");
    private String message;



    ReservationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
