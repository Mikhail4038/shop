package com.keiko.orderservice.request;

import com.keiko.orderservice.entity.resources.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RouteDetailsRequest {
    private Address from;
    private Address to;
}
