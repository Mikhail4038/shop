package com.keiko.shopservice.exception.model;

public class ProductStockLevelException extends RuntimeException {
    public ProductStockLevelException (String message) {
        super (message);
    }
}
