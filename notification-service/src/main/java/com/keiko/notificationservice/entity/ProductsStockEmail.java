package com.keiko.notificationservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsStockEmail extends SimpleEmail {
    private List<ProductStock> productsStock;
}
