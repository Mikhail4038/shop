package com.keiko.orderservice.exception.model;

public class OrderProcessException extends RuntimeException {
    public OrderProcessException (String message) {
        super (message);
    }
}
