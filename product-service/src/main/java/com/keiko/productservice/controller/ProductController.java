package com.keiko.productservice.controller;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.productservice.constants.WebResourceKeyConstants.PRODUCT_BASE;

@RestController
@RequestMapping (value = PRODUCT_BASE)
public class ProductController extends CrudController<Product, ProductDto> {
}
