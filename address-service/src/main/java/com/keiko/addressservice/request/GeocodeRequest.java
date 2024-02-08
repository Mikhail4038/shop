package com.keiko.addressservice.request;

import com.keiko.addressservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodeRequest {
    private Address address;
}
