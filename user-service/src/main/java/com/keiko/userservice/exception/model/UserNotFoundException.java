package com.keiko.userservice.exception.model;

public class UserNotFoundException
        extends RuntimeException {

    public UserNotFoundException (String message) {
        super (message);
    }
}
