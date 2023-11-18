package com.keiko.authservice.exception.model;

public class JwtRefreshTokenProcessException extends RuntimeException {

    public JwtRefreshTokenProcessException (String message) {
        super (message);
    }
}
