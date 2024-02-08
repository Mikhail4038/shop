package com.keiko.shopservice.entity.resources;

import com.keiko.shopservice.entity.ProductStock;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductStockEmail {
    private String toAddress;
    private String subject;
    private String message;
    private List<ProductStock> productStocks;
}
