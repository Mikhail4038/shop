package com.keiko.shopservice.controller;

import com.keiko.shopservice.entity.resources.BookingOrderEntryRequest;
import com.keiko.shopservice.entity.resources.SellingOrderEntryRequest;
import com.keiko.shopservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity booked (@RequestBody BookingOrderEntryRequest bookedRequest) {
        productStockService.bookedStock (bookedRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = CANCEL_BOOKED_STOCK)
    public ResponseEntity cancelBooked (@RequestBody BookingOrderEntryRequest cancelBookedRequest) {
        productStockService.cancelBookedStock (cancelBookedRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = SELL_STOCK)
    public ResponseEntity sellProductStocks (@RequestBody SellingOrderEntryRequest sellingRequest) {
        productStockService.sellStock (sellingRequest);
        return ResponseEntity.ok ().build ();
    }
}
