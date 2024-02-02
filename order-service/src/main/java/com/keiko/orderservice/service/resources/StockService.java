package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.request.BookingStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.orderservice.constants.MicroServiceConstants.STOCK_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = STOCK_SERVICE)
public interface StockService {

    @GetMapping (value = PRODUCT_STOCK_BASE + COUNT_PRODUCT_STOCK_FOR_SELL)
    Long countProductForSell (@RequestParam String ean);

    @PostMapping (value = PRODUCT_STOCK_BASE + BOOKED_STOCK)
    void bookedStock (@RequestBody BookingStockRequest request);

    @PostMapping (value = PRODUCT_STOCK_BASE + CANCEL_BOOKED_STOCK)
    void cancelBookedStock (@RequestBody BookingStockRequest request);
}
