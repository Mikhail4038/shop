package com.keiko.shopservice.controller;

import com.keiko.shopservice.dto.model.productStock.ProductStockDto;
import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.resources.OrderEntry;
import com.keiko.shopservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.keiko.shopservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PRODUCT_STOCK_BASE)
public class ProductStockController
        //extends AbstractCrudController<ProductStock, ProductStockDto>
{

    @Autowired
    private ProductStockService productStockService;

    @GetMapping (value = COUNT_PRODUCT_STOCK_FOR_SELL)
    public ResponseEntity<Long> stockForSell (@RequestParam String ean) {
        Long availableStock = productStockService.countProductStockForSell (ean);
        return ResponseEntity.ok (availableStock);
    }

    @PostMapping (value = BOOKED_STOCK)
    public ResponseEntity booked (@RequestBody OrderEntry orderEntry) {
        productStockService.bookedStock (orderEntry);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = CANCEL_BOOKED_STOCK)
    public ResponseEntity cancelBooked (@RequestBody OrderEntry orderEntry) {
        productStockService.cancelBookedStock (orderEntry);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = SELL_STOCK)
    public ResponseEntity sellProductStocks (@RequestBody List<OrderEntry> entries) {
        productStockService.sellStock (entries);
        return ResponseEntity.ok ().build ();
    }
}
