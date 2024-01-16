package com.keiko.stockservice.controller;

import com.keiko.stockservice.dto.ProductStockDto;
import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

import static com.keiko.stockservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping (value = PRODUCT_STOCK_BASE)
public class ProductStockController {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private Function<ProductStock, ProductStockDto> toDtoConverter;

    @PostMapping (value = SAVE)
    public ResponseEntity save (@RequestBody ProductStock productStock) {
        productStockService.save (productStock);
        return ResponseEntity.status (CREATED).build ();
    }

    @GetMapping (value = FETCH_BY)
    public ResponseEntity<ProductStockDto> fetchBy (@RequestParam Long id) {
        ProductStock productStock = productStockService.fetchById (id);
        ProductStockDto dto = toDtoConverter.apply (productStock);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = FETCH_ALL)
    public ResponseEntity<List<ProductStockDto>> fetchAll () {
        List<ProductStock> productStocks = productStockService.fetchAll ();
        List<ProductStockDto> dto = productStocks.stream ()
                .map (toDtoConverter::apply)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }

    @DeleteMapping (value = DELETE)
    public ResponseEntity delete (@RequestParam Long id) {
        productStockService.delete (id);
        return ResponseEntity.ok ().build ();
    }
}
