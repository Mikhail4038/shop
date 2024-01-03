package com.keiko.productservice.dto.converter.review;

import com.keiko.productservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewToDataConverter extends AbstractToDtoConverter<Review, ReviewData> {

    public ReviewToDataConverter () {
        super (Review.class, ReviewData.class);
    }

    @Override
    public void mapSpecificFields (Review entity, ReviewData dto) {
    }
}
