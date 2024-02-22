package com.keiko.commonservice.entity.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntry {
    private String productEan;
    private Long quantity;
}
