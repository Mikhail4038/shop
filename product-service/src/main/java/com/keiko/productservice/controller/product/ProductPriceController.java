package com.keiko.productservice.controller.product;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keiko.productservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PRODUCT_BASE + PRICE_BASE)
public class ProductPriceController extends ProductController {

    @Autowired
    private ProductPriceService productPriceService;

    @GetMapping (value = PRICE_LESS_THAN)
    public ResponseEntity<List<ProductDto>> findProductsPriceLess (@RequestParam Double price,
                                                                   @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productPriceService.findProductsPriceLessThan (price, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = PRICE_MORE_THAN)
    public ResponseEntity<List<ProductDto>> findProductsPriceMore (@RequestParam Double price,
                                                                   @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productPriceService.findProductsPriceMoreThan (price, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = PRICE_RANGE)
    public ResponseEntity<List<ProductDto>> findProductsPriceRange (
            @RequestParam Double minPrice, Double maxPrice,
            @RequestParam (required = false) Boolean sortByAscend) {

        List<Product> products = productPriceService.findProductsPriceRange (minPrice, maxPrice, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

}
