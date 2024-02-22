package com.keiko.productservice.dto.converter.review;

import com.keiko.commonservice.dto.converter.AbstractToDtoConverter;
import com.keiko.commonservice.entity.resource.User;
import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.service.resources.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewToDataConverter extends AbstractToDtoConverter<Review, ReviewData> {

    @Autowired
    private UserService userService;

    public ReviewToDataConverter () {
        super (Review.class, ReviewData.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (Review.class, ReviewData.class)
                .setPostConverter (converter);
    }

    @Override
    public void mapSpecificFields (Review review, ReviewData data) {
        final Long userId = review.getUserId ();
        User user = userService.fetchBy (userId);
        data.setUser (user);
    }
}
