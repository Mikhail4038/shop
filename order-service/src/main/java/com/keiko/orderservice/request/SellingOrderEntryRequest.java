package com.keiko.orderservice.request;

import com.keiko.orderservice.entity.OrderEntry;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SellingOrderEntryRequest {
    private List<OrderEntry> entries;
    private Long shopId;
}
