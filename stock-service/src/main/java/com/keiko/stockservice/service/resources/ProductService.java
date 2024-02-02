package com.keiko.stockservice.service.resources;

import com.keiko.stockservice.entity.resources.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.stockservice.constants.MicroServiceConstants.PRODUCT_SERVICE;
import static com.keiko.stockservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = PRODUCT_SERVICE)
public interface ProductService {

    @GetMapping (value = PRODUCT_BASE + BY_EAN)
    Product findByEan (@RequestParam String ean);

    @GetMapping (value = PRODUCT_BASE + IS_EXIST)
    Boolean isExist (@RequestParam String ean);
}
