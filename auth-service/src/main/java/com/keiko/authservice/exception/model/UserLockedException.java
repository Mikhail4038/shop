package com.keiko.authservice.exception.model;

public class UserLockedException extends RuntimeException {
    public UserLockedException (String message) {
        super (message);
    }
}
