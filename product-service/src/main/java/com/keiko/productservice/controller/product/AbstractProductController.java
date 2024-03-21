package com.keiko.productservice.controller.product;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
public class AbstractProductController {

    @Autowired
    private Function<Product, ProductDto> toDtoConverter;

    public List<ProductDto> convertToDto (List<Product> products) {
        return products.stream ()
                .map (toDtoConverter::apply)
                .collect (toList ());
    }
}
