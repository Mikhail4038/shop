package com.keiko.commonservice.entity.resource.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    private String ean;
    private String name;
    private Price price;
    private Rating rating;
    private List<Review> reviews;
    private Producer producer;
}