package com.keiko.userservice.exception.model;

public class RoleNotFoundException
        extends RuntimeException {

    public RoleNotFoundException (String message) {
        super (message);
    }
}
