package com.keiko.productservice.dto.converter.product;

import com.keiko.productservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductToDtoConverter extends AbstractToDtoConverter<Product, ProductDto> {

    public ProductToDtoConverter () {
        super (Product.class, ProductDto.class);
    }

    @Override
    public void mapSpecificFields (Product product, ProductDto dto) {
    }
}
