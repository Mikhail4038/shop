package com.keiko.paymentservice.exception.model;

public class PaymentOrderException extends RuntimeException {
    public PaymentOrderException (String message) {
        super (message);
    }
}
