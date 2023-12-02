package com.keiko.productservice.controller.product;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.ProductPromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keiko.productservice.constants.WebResourceKeyConstants.PROMO_BASE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.PROMO_PRODUCTS;

@RestController
@RequestMapping (value = PROMO_BASE)
public class ProductPromoController extends ProductController {

    @Autowired
    private ProductPromoService productPromoService;

    @GetMapping (value = PROMO_PRODUCTS)
    public ResponseEntity<List<ProductDto>> findPromoProducts (@RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productPromoService.findPromoProducts (sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }
}
