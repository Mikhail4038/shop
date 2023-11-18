package com.keiko.authservice.exception.model;

public class VerificationTokenProcessException
        extends RuntimeException {

    public VerificationTokenProcessException (String message) {
        super (message);
    }
}
