package com.keiko.addressservice.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GeocodeResponse {
    private String lat;
    private String lng;
}
