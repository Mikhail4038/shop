package com.keiko.addressservice.request;

import com.keiko.commonservice.entity.resource.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeocodeRequest {
    private Address address;
}
