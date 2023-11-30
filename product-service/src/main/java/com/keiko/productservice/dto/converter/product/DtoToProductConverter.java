package com.keiko.productservice.dto.converter.product;

import com.keiko.productservice.dto.converter.AbstractToEntityConverter;
import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Rating;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class DtoToProductConverter extends AbstractToEntityConverter<ProductDto, Product> {

    public DtoToProductConverter () {
        super (ProductDto.class, Product.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (ProductDto.class, Product.class)
                .addMappings (mapper -> mapper.skip (Product::setRating))
                .addMappings (mapper -> mapper.skip (Product::setReviews))
                .setPostConverter (converter);
    }

    @Override
    protected void mapSpecificFields (ProductDto dto, Product product) {
        if (isNull (dto.getId ())) {
            Rating rating = new Rating ();
            product.setRating (rating);
        }
    }
}
