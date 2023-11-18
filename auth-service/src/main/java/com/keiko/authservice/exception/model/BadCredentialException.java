package com.keiko.authservice.exception.model;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException (String message) {
        super (message);
    }
}
