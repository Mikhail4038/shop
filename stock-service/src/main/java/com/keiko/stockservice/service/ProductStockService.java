package com.keiko.stockservice.service;

import com.keiko.stockservice.entity.ProductStock;

import java.util.Collection;
import java.util.List;

public interface ProductStockService {
    List<ProductStock> fetchByEan (String ean);

    Long countProductInStock (String ean);

    void reduceStockLevel (String ean, Long value);

    List<ProductStock> findProductStocksToMoveExpiredStopList ();

    void saveAll (Collection<ProductStock> productStocks);
}
