package com.keiko.shopservice.service;

import com.keiko.commonservice.request.StockOrderEntryRequest;
import com.keiko.shopservice.entity.ProductStock;

import java.util.Collection;
import java.util.List;

public interface ProductStocksService {

    Long countProductStockForSell (String ean, Long shopId);

    void bookStock (StockOrderEntryRequest bookEntryRequest);

    void cancelBookStock (StockOrderEntryRequest cancelBookEntryRequest);

    void sellStock (StockOrderEntryRequest sellEntryRequest);

    List<ProductStock> findProductStocksToMoveExpiredStopList (Long shopId);

    void saveAll (Collection<ProductStock> productStocks);
}
