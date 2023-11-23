package com.keiko.addressservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodeResponse {
    private String lat;
    private String lng;
}
