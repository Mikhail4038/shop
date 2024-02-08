package com.keiko.addressservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReverseGeocodeRequest {
    private String lat;
    private String lng;
}
