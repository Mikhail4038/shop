package com.keiko.stockservice.controller;

import com.keiko.stockservice.dto.productStock.ProductStockDto;
import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.request.BookingStockRequest;
import com.keiko.stockservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.keiko.stockservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PRODUCT_STOCK_BASE)
public class ProductStockController
        extends AbstractCrudController<ProductStock, ProductStockDto> {

    @Autowired
    private ProductStockService productStockService;

    @GetMapping (value = COUNT_PRODUCT_STOCK_FOR_SELL)
    public ResponseEntity<Long> stockForSell (@RequestParam String ean) {
        Long availableStock = productStockService.countProductStockForSell (ean);
        return ResponseEntity.ok (availableStock);
    }


    @PostMapping (value = BOOKED_STOCK)
    public ResponseEntity booked (@RequestBody BookingStockRequest request) {
        productStockService.bookedStock (request);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = CANCEL_BOOKED_STOCK)
    public ResponseEntity cancelBooked (@RequestBody BookingStockRequest request) {
        productStockService.cancelBookedStock (request);
        return ResponseEntity.ok ().build ();
    }
}
