package com.keiko.productservice.dto.model.product;

import com.keiko.productservice.dto.model.BaseDto;
import com.keiko.productservice.dto.model.rating.RatingDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProductData extends BaseDto {
    private String ean;
    private String name;
    private Double price;
    private Timestamp expirationDate;
    private RatingDto rating;
}
