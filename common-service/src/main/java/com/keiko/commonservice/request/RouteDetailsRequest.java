package com.keiko.commonservice.request;

import com.keiko.commonservice.entity.resource.Address;
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
