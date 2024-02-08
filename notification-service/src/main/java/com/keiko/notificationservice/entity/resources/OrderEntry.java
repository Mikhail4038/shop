package com.keiko.notificationservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntry {
    private String productEan;
    private Long quantity;
}
