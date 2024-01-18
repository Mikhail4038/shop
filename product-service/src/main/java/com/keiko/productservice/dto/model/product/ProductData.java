package com.keiko.productservice.dto.model.product;

import com.keiko.productservice.dto.model.BaseDto;
import com.keiko.productservice.dto.model.price.PriceDto;
import com.keiko.productservice.dto.model.rating.RatingDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData extends BaseDto {
    private String ean;
    private String name;
    private PriceDto price;
    private RatingDto rating;
}
