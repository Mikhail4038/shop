package com.keiko.stockservice.dto;

import com.keiko.stockservice.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockDto {
    private Product product;
    private Double balance;
}
