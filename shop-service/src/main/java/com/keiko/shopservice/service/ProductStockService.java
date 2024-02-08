package com.keiko.shopservice.service;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.resources.OrderEntry;

import java.util.Collection;
import java.util.List;

public interface ProductStockService {
    List<ProductStock> fetchByEan (String ean);

    Long countProductStockForSell (String ean);

    void bookedStock (OrderEntry orderEntry);

    void cancelBookedStock (OrderEntry orderEntry);

    void sellStock (List<OrderEntry> entries);

    List<ProductStock> findProductStocksToMoveExpiredStopList ();

    void saveAll (Collection<ProductStock> productStocks);
}
