package com.keiko.stockservice.dto.productStock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keiko.stockservice.dto.BaseDto;
import com.keiko.stockservice.entity.resources.Product;
import com.keiko.stockservice.entity.StopList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductStockDto extends BaseDto {
    private Product product;
    private Double balance;

    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    private StopList stopList;
}
