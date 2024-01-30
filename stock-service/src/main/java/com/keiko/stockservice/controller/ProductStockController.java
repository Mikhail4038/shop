package com.keiko.stockservice.controller;

import com.keiko.stockservice.dto.productStock.ProductStockDto;
import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping (value = REDUCE_STOCK_LEVEL)
    public ResponseEntity reduceStock (@RequestParam String ean,
                                       @RequestParam Long value) {
        productStockService.reduceStockLevel (ean, value);
        return ResponseEntity.ok ().build ();
    }
}
