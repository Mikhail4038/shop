package com.keiko.shopservice.controller;

import com.keiko.commonservice.request.StockOrderEntryRequest;
import com.keiko.shopservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.PRODUCT_STOCK_BASE;
import static com.keiko.shopservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PRODUCT_STOCK_BASE)
public class ProductStockController {

    @Autowired
    private ProductStockService productStockService;

    @GetMapping (value = COUNT_PRODUCT_STOCK_FOR_SELL)
    public ResponseEntity<Long> stockForSell (@RequestParam String ean, @RequestParam Long shopId) {
        Long availableStock = productStockService.countProductStockForSell (ean, shopId);
        return ResponseEntity.ok (availableStock);
    }

    @PostMapping (value = BOOKED_STOCK)
    public ResponseEntity booked (@RequestBody StockOrderEntryRequest bookEntryRequest) {
        productStockService.bookStock (bookEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = CANCEL_BOOKED_STOCK)
    public ResponseEntity cancelBooked (@RequestBody StockOrderEntryRequest cancelBookEntryRequest) {
        productStockService.cancelBookedStock (cancelBookEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = SELL_STOCK)
    public ResponseEntity sellProductStocks (@RequestBody StockOrderEntryRequest sellEntryRequest) {
        productStockService.sellStock (sellEntryRequest);
        return ResponseEntity.ok ().build ();
    }
}
