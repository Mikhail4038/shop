package com.keiko.orderservice.entity.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String email;
    private String name;
    private UserAddress userAddress;

    @Getter
    @Setter
    @NoArgsConstructor
    private class UserAddress {
        private String street;
        private String house;
        private String city;
        private String country;
        private String zipCode;
    }
}
