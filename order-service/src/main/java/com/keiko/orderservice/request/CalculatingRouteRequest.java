package com.keiko.orderservice.request;

import com.keiko.orderservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculatingRouteRequest {
    private Address from;
    private Address to;
}
