package com.keiko.orderservice.dto.model;

import com.keiko.commonservice.dto.model.BaseDto;
import com.keiko.commonservice.entity.resource.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntryDto extends BaseDto {
    private Product product;
    private Long quantity;
}
