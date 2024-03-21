package com.keiko.shopservice.service.resources;

import com.keiko.commonservice.entity.resource.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.commonservice.constants.MicroServiceConstants.PRODUCT_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = PRODUCT_SERVICE)
public interface ProductService {

    @GetMapping (value = PRODUCT_BASE + FIND_BY_EAN)
    Product findByEan (@RequestParam String ean);

    @GetMapping (value = PRODUCT_BASE + IS_EXIST)
    Boolean isExist (@RequestParam String ean);
}
