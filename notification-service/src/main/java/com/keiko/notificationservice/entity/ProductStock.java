package com.keiko.notificationservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStock {
    private String ean;
    private Double balance;
}
