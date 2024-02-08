package com.keiko.notificationservice.entity;

import com.keiko.notificationservice.entity.resources.ProductStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductStocksEmail extends SimpleEmail {
    private List<ProductStock> productStocks;
}
