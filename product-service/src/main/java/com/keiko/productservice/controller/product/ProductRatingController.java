package com.keiko.productservice.controller.product;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.ProductRatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.PRODUCT_BASE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PRODUCT_BASE + RATING_BASE)
@Tag (name = "Product rating API")
public class ProductRatingController extends AbstractProductController {

    @Autowired
    private ProductRatingService productRatingService;

    @GetMapping (value = RATING_LESS_THAN)
    public ResponseEntity<List<ProductDto>> findProductsRatingLess (@RequestParam Float rating,
                                                                    @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productRatingService.findProductsRatingLessThan (rating, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = RATING_MORE_THAN)
    public ResponseEntity<List<ProductDto>> findProductsRatingMore (@RequestParam Float rating,
                                                                    @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productRatingService.findProductsRatingMoreThan (rating, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = RANGE)
    public ResponseEntity<List<ProductDto>> findProductsRatingRange (
            @RequestParam Float minRating, Float maxRating,
            @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productRatingService.findProductsRatingRange (minRating, maxRating, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }
}
