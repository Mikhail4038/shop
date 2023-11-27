package com.keiko.productservice.dto.converter.review;

import com.keiko.productservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewToDtoConverter
        extends AbstractToDtoConverter<Review, ReviewDto> {

    public ReviewToDtoConverter () {
        super (Review.class, ReviewDto.class);
    }

    @Override
    public void mapSpecificFields (Review review, ReviewDto dto) {
    }
}
