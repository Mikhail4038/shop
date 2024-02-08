package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.entity.OrderEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.keiko.orderservice.constants.MicroServiceConstants.STOCK_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = STOCK_SERVICE)
public interface StockService {

    @GetMapping (value = PRODUCT_STOCK_BASE + COUNT_PRODUCT_STOCK_FOR_SELL)
    Long countProductForSell (@RequestParam String ean);

    @PostMapping (value = PRODUCT_STOCK_BASE + BOOKED_STOCK)
    void bookedStock (@RequestBody OrderEntry orderEntry);

    @PostMapping (value = PRODUCT_STOCK_BASE + CANCEL_BOOKED_STOCK)
    void cancelBookedStock (@RequestBody OrderEntry OrderEntry);

    @PostMapping (value = PRODUCT_STOCK_BASE + SELL_STOCK)
    void sellProductStocks (@RequestBody List<OrderEntry> entries);
}
