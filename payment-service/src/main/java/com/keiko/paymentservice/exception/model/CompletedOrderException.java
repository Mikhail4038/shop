package com.keiko.paymentservice.exception.model;

public class CompletedOrderException extends RuntimeException {
    public CompletedOrderException (String message) {
        super (message);
    }
}
