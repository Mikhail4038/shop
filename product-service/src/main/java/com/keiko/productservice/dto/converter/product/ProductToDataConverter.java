package com.keiko.productservice.dto.converter.product;

import com.keiko.productservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.product.ProductData;
import com.keiko.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductToDataConverter extends AbstractToDtoConverter<Product, ProductData> {

    public ProductToDataConverter () {
        super (Product.class, ProductData.class);
    }

    @Override
    public void mapSpecificFields (Product entity, ProductData dto) {

    }
}
