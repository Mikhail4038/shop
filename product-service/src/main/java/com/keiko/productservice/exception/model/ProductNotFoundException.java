package com.keiko.productservice.exception.model;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException (String message) {
        super (message);
    }
}
