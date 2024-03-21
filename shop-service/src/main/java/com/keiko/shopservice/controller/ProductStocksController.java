package com.keiko.shopservice.controller;

import com.keiko.commonservice.request.StockOrderEntryRequest;
import com.keiko.shopservice.service.ProductStocksService;
import com.keiko.shopservice.service.UploadProductStockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;
import static com.keiko.shopservice.constants.WebResourceKeyConstants.UPLOAD;

@RestController
@RequestMapping (value = PRODUCT_STOCK_BASE)
@Tag (name = "Product stocks API")
public class ProductStocksController {

    @Autowired
    private ProductStocksService productStocksService;

    @Autowired
    private UploadProductStockService uploadProductStockService;

    @PostMapping (value = UPLOAD)
    public ResponseEntity uploading (@RequestParam MultipartFile file,
                                     @RequestParam Long shopId) {
        uploadProductStockService.uploadProductStocks (file, shopId);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = COUNT_PRODUCT_STOCK_FOR_SELL)
    public ResponseEntity<Long> countStockForSell (@RequestParam String ean, @RequestParam Long shopId) {
        Long availableStock = productStocksService.countProductStockForSell (ean, shopId);
        return ResponseEntity.ok (availableStock);
    }

    @PostMapping (value = BOOK_STOCK)
    public ResponseEntity bookStock (@RequestBody StockOrderEntryRequest bookEntryRequest) {
        productStocksService.bookStock (bookEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = CANCEL_BOOK_STOCK)
    public ResponseEntity cancelBookStock (@RequestBody StockOrderEntryRequest cancelBookEntryRequest) {
        productStocksService.cancelBookStock (cancelBookEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = SELL_STOCK)
    public ResponseEntity sellProductStocks (@RequestBody StockOrderEntryRequest sellEntryRequest) {
        productStocksService.sellStock (sellEntryRequest);
        return ResponseEntity.ok ().build ();
    }
}
