package com.keiko.commonservice.entity.resource.stock;

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
    private List<ProductStockData> productStocks;
}
