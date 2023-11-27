package com.keiko.productservice.dto.converter.review;

import com.keiko.productservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class DtoToReviewConverter
        extends AbstractToDtoConverter<ReviewDto, Review> {

    public DtoToReviewConverter () {
        super (ReviewDto.class, Review.class);
    }

    @Override
    public void mapSpecificFields (ReviewDto dto, Review review) {
    }
}
