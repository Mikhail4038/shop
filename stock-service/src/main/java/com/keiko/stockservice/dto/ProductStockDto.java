package com.keiko.stockservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keiko.stockservice.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductStockDto {
    private Product product;
    private Double balance;

    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
}
