package com.keiko.productservice.controller.product;

import com.keiko.commonservice.controller.DefaultCrudController;
import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;
import static com.keiko.productservice.constants.WebResourceKeyConstants.ADVANCED_SEARCH;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping (value = PRODUCT_BASE)
@Tag (name = "Products API")
public class ProductController extends DefaultCrudController<Product, ProductDto> {

    @Autowired
    private ProductService productService;

    @GetMapping (value = ADVANCED_SEARCH)
    public ResponseEntity<List<ProductDto>> advancedSearch (@RequestParam (required = false) Long producerId,
                                                            @RequestParam (required = false) boolean isPromotional,
                                                            @RequestParam (required = false) Double minPrice,
                                                            @RequestParam (required = false) Double maxPrice,
                                                            @RequestParam (required = false) Float minRating,
                                                            @RequestParam (required = false) Float maxRating) {
        List<Product> products = productService.advancedSearch (producerId, isPromotional, minPrice, maxPrice, minRating, maxRating);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = FIND_BY_EAN)
    public ResponseEntity<ProductDto> findByEan (@RequestParam String ean) {
        Product product = productService.findByEan (ean);
        ProductDto dto = getToDtoConverter ().apply (product);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = IS_EXIST)
    public Boolean isExist (@RequestParam String ean) {
        return productService.isExist (ean);
    }

    protected List<ProductDto> convertToDto (List<Product> products) {
        return products.stream ()
                .map (getToDtoConverter ()::apply)
                .collect (toList ());
    }
}
