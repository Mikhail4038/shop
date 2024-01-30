package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.entity.resources.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.orderservice.constants.MicroServiceConstants.PRODUCT_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.BY_EAN;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.PRODUCT_BASE;

@Service
@FeignClient (name = PRODUCT_SERVICE)
public interface ProductService {

    @GetMapping (value = PRODUCT_BASE + BY_EAN)
    Product findByEan (@RequestParam String ean);
}
