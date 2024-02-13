package com.keiko.orderservice.entity.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Shop {
    private Long id;
    private ShopAddress shopAddress;

    @Getter
    @Setter
    @NoArgsConstructor
    private class ShopAddress {
        private String street;
        private String house;
        private String city;
        private String country;
        private String zipCode;
    }
}
