package com.keiko.addressservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReverseGeocodeResponse {
    private String street;
    private String house;
    private String city;
    private String country;
    private String postcode;
}
