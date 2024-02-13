package com.keiko.orderservice.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingOrderEntryRequest {
    private String ean;
    private Long quantity;
    private Long shopId;
}
