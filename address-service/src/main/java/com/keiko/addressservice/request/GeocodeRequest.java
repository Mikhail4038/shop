package com.keiko.addressservice.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GeocodeRequest {
    private String street;
    private String house;
    private String city;
    private String country;
    private String locale;
}
