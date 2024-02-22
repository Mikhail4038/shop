package com.keiko.productservice.dto.model.price;

import com.keiko.commonservice.dto.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto extends BaseDto {
    private Double value;
    private boolean isPromotional;
}
