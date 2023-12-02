package com.keiko.productservice.dto.model.price;

import com.keiko.productservice.dto.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto extends BaseDto {
    private Double cost;
    private boolean isPromotional;
}
