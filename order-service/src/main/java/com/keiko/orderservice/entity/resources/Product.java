package com.keiko.orderservice.entity.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    private String ean;
    private String name;
    private Price price;
}
