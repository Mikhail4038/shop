package com.keiko.stockservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    private String ean;
    private String name;
    private Price price;
    private Rating rating;
    private Producer producer;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp expirationDate;

    @Getter
    @Setter
    @NoArgsConstructor
    private class Price {
        private Double value;
        private boolean isPromotional;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class Rating {
        private Float value;
        private Integer countReviews;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class Producer {
        private String name;
    }
}