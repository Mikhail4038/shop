package com.keiko.orderservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductStock {
    private Product product;
    private Double balance;

    @Getter
    @Setter
    @NoArgsConstructor
    private class Product {
        private String ean;
        private String name;
        private Price price;

        @Getter
        @Setter
        @NoArgsConstructor
        private class Price {
            private Double value;
            private boolean isPromotional;
        }
    }
}
