package com.keiko.notificationservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductStock {
    private String ean;
    private Double balance;
    private LocalDate expirationDate;
}
