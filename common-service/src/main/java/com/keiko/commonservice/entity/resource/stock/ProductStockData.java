package com.keiko.commonservice.entity.resource.stock;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductStockData {
    private String ean;
    private Long balance;
    private LocalDate expirationDate;

}
