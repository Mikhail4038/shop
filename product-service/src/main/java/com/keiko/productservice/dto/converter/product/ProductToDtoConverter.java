package com.keiko.productservice.dto.converter.product;

import com.keiko.commonservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Review;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
public class ProductToDtoConverter extends AbstractToDtoConverter<Product, ProductDto> {

    @Autowired
    private Function<Review, ReviewData> toReviewDataConverter;

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (Product.class, ProductDto.class)
                .setPostConverter (converter);
    }

    public ProductToDtoConverter () {
        super (Product.class, ProductDto.class);
    }

    @Override
    public void mapSpecificFields (Product product, ProductDto dto) {
        List<Review> reviews = product.getReviews ();
        List<ReviewData> reviewsData = reviews.stream
                ().map (toReviewDataConverter::apply)
                .collect (toList ());
        dto.setReviews (reviewsData);
    }
}
