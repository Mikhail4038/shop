package com.keiko.orderservice.service.resources;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.orderservice.constants.MicroServiceConstants.STOCK_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = STOCK_SERVICE)
public interface StockService {

    @GetMapping (value = PRODUCT_STOCK_BASE + COUNT_PRODUCT_STOCK_FOR_SELL)
    Long countProductForSell (@RequestParam String ean);

    @GetMapping (value = PRODUCT_STOCK_BASE + REDUCE_STOCK_LEVEL)
    public ResponseEntity reduceStock (@RequestParam String ean,
                                       @RequestParam Long value);
}
