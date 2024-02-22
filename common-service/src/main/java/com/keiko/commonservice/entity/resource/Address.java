package com.keiko.commonservice.entity.resource;

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
