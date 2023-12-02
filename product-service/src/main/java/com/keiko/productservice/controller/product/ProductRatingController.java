package com.keiko.productservice.controller.product;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keiko.productservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PRODUCT_BASE + RATING_BASE)
public class ProductRatingController extends ProductController {

    @Autowired
    private ProductRatingService productRatingService;

    @GetMapping (value = RATING_LESS_THAN)
    public ResponseEntity<List<ProductDto>> findProductsRatingLess (@RequestParam Double averageAssessment,
                                                                    @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productRatingService.findProductsRatingLessThan (averageAssessment, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = RATING_MORE_THAN)
    public ResponseEntity<List<ProductDto>> findProductsRatingMore (@RequestParam Double averageAssessment,
                                                                    @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productRatingService.findProductsRatingMoreThan (averageAssessment, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = RATING_RANGE)
    public ResponseEntity<List<ProductDto>> findProductsRatingRange (
            @RequestParam Float minAverageAssessment, Float maxAverageAssessment,
            @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productRatingService.findProductsRatingRange (minAverageAssessment, maxAverageAssessment, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }
}
