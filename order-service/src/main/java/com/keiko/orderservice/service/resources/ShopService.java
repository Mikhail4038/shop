package com.keiko.orderservice.service.resources;

import com.keiko.commonservice.entity.resource.Shop;
import com.keiko.commonservice.request.StockOrderEntryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.commonservice.constants.MicroServiceConstants.SHOP_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.PRODUCT_STOCK_BASE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.SHOP_BASE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = SHOP_SERVICE)
public interface ShopService {

    @GetMapping (value = PRODUCT_STOCK_BASE + COUNT_PRODUCT_STOCK_FOR_SELL)
    Long countProductForSell (@RequestParam String ean, @RequestParam Long shopId);

    @PostMapping (value = PRODUCT_STOCK_BASE + BOOKED_STOCK)
    void bookStock (@RequestBody StockOrderEntryRequest bookEntryRequest);

    @PostMapping (value = PRODUCT_STOCK_BASE + CANCEL_BOOKED_STOCK)
    void cancelBookStock (@RequestBody StockOrderEntryRequest cancelBookEntryRequest);

    @PostMapping (value = PRODUCT_STOCK_BASE + SELL_STOCK)
    void sellProductStocks (@RequestBody StockOrderEntryRequest sellEntryRequest);

    @GetMapping (value = SHOP_BASE + FETCH_SHOP_BY_ID)
    Shop fetchBy (@RequestParam Long id);
}
