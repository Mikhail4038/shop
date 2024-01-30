package com.keiko.stockservice.exception.model;

public class ProductStockLevelException extends RuntimeException {
    public ProductStockLevelException (String message) {
        super (message);
    }
}
