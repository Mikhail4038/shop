package com.keiko.addressservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String street;
    private String house;
    private String city;
    private String country;
    private String postcode;
}
