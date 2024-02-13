package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.entity.resources.Shop;
import com.keiko.orderservice.request.BookingOrderEntryRequest;
import com.keiko.orderservice.request.SellingOrderEntryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.orderservice.constants.MicroServiceConstants.SHOP_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = SHOP_SERVICE)
public interface ShopService {

    @GetMapping (value = PRODUCT_STOCK_BASE + COUNT_PRODUCT_STOCK_FOR_SELL)
    Long countProductForSell (@RequestParam String ean, @RequestParam Long shopId);

    @PostMapping (value = PRODUCT_STOCK_BASE + BOOKED_STOCK)
    void bookedStock (@RequestBody BookingOrderEntryRequest bookedRequest);

    @PostMapping (value = PRODUCT_STOCK_BASE + CANCEL_BOOKED_STOCK)
    void cancelBookedStock (@RequestBody BookingOrderEntryRequest cancelBookedRequest);

    @PostMapping (value = PRODUCT_STOCK_BASE + SELL_STOCK)
    void sellProductStocks (@RequestBody SellingOrderEntryRequest sellingRequest);

    @GetMapping (value = SHOP_BASE + FETCH_SHOP_BY_ID)
    Shop fetchBy (@RequestParam Long id);
}
