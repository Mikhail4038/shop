package com.keiko.productservice.controller.product;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.ProductProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keiko.productservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PRODUCT_BASE + PRODUCT_PRODUCER_BASE)
public class ProductProducerController extends ProductController {

    @Autowired
    private ProductProducerService productProducerService;

    @GetMapping (value = BY_PRODUCER)
    public ResponseEntity<List<ProductDto>> findByProducer (@RequestParam Long producerId,
                                                            @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productProducerService.findProductsByProducer (producerId, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = PROMO_BY_PRODUCER)
    public ResponseEntity<List<ProductDto>> findPromoProductByProducer (@RequestParam Long producerId,
                                                                        @RequestParam (required = false) Boolean sortByAscend) {
        List<Product> products = productProducerService.findPromoProductByProducer (producerId, sortByAscend);
        List<ProductDto> dto = convertToDto (products);
        return ResponseEntity.ok (dto);
    }

}
