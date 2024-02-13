package com.keiko.orderservice.request;

import com.keiko.orderservice.entity.DeliveryAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CalculatingRouteRequest {
    private DeliveryAddress from;
    private DeliveryAddress to;
}
