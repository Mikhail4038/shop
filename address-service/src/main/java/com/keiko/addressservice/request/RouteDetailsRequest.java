package com.keiko.addressservice.request;

import com.keiko.addressservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDetailsRequest {
    private Address from;
    private Address to;
}
