package com.keiko.commonservice.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ReverseGeocodeRequest {
    private String lat;
    private String lng;
}
