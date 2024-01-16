package com.keiko.stockservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductsStockEmail {
    private String toAddress;
    private String subject;
    private String message;
    private List<ProductStock> productsStock;
}
