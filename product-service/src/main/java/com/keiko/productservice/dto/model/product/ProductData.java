package com.keiko.productservice.dto.model.product;

import com.keiko.commonservice.dto.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData extends BaseDto {
    private String ean;
    private String name;
}
