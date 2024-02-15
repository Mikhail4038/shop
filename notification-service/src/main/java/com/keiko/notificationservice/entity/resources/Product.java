package com.keiko.notificationservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String ean;
    private String name;
    private Price price;
}
