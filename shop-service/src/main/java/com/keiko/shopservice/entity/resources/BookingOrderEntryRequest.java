package com.keiko.shopservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingOrderEntryRequest {
    private String ean;
    private Long quantity;
    private Long shopId;
}
