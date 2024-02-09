package com.keiko.notificationservice.entity;

import com.keiko.notificationservice.entity.resources.ProductStockData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductStocksEmail extends SimpleEmail {
    private List<ProductStockData> productStocks;
}
