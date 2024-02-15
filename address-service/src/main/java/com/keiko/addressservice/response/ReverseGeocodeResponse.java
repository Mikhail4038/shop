package com.keiko.addressservice.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReverseGeocodeResponse {
    private String street;
    private String house;
    private String city;
    private String country;
    private String postcode;
}
