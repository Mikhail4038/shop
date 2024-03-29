package com.keiko.shopservice.dto.model.productStock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keiko.commonservice.dto.model.BaseDto;
import com.keiko.commonservice.entity.resource.product.Product;
import com.keiko.shopservice.entity.StopList;
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
