package com.keiko.commonservice.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockOrderEntryRequest {
    private String ean;
    private Long quantity;
    private Long shopId;
}
