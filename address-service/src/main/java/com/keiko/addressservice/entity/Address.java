package com.keiko.addressservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Address {
    private String street;
    private String house;
    private String city;
    private String country;
    //zipCode
    private String locale;
}
