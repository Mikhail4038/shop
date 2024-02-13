package com.keiko.shopservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellingOrderEntryRequest {
    private List<OrderEntry> entries;
    private Long shopId;
}
