package net.erickcaron.customerapplication.exception;

public class CustomerException extends RuntimeException {

    private CustomerError customerError;

    public CustomerException(CustomerError customerError) {
        this.customerError = customerError;
    }


    public CustomerError getCustomerError() {
        return customerError;
    }
}
