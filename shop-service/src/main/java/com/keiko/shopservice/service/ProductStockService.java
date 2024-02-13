package com.keiko.shopservice.service;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.resources.BookingOrderEntryRequest;
import com.keiko.shopservice.entity.resources.SellingOrderEntryRequest;

import java.util.Collection;
import java.util.List;

public interface ProductStockService {
    Long countProductStockForSell (String ean, Long shopId);

    void bookedStock (BookingOrderEntryRequest bookedRequest);

    void cancelBookedStock (BookingOrderEntryRequest cancelBookedRequest);

    void sellStock (SellingOrderEntryRequest sellingRequest);

    List<ProductStock> findProductStocksToMoveExpiredStopList (Long shopId);

    void saveAll (Collection<ProductStock> productStocks);
}
