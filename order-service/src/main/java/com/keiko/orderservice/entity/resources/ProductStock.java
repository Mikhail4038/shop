package com.keiko.orderservice.entity.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductStock {
    private Product product;
    private Double balance;
}
