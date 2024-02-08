package com.keiko.orderservice.dto.model.order;

import com.keiko.orderservice.entity.resources.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntryDto {
    private Product product;
    private Long quantity;
}
