package com.keiko.authservice.exception.model;

public class UserAlreadyExistException
        extends RuntimeException {

    public UserAlreadyExistException (String message) {
        super (message);
    }
}
