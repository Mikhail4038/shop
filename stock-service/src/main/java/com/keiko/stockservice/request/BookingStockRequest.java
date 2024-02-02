package com.keiko.stockservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingStockRequest {
    private String ean;
    private Long value;
}
