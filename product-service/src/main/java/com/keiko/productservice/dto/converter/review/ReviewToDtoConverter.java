package com.keiko.productservice.dto.converter.review;

import com.keiko.commonservice.dto.converter.AbstractToDtoConverter;
import com.keiko.commonservice.entity.resource.user.User;
import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.service.resources.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewToDtoConverter extends AbstractToDtoConverter<Review, ReviewDto> {

    @Autowired
    private UserService userService;

    public ReviewToDtoConverter () {
        super (Review.class, ReviewDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (Review.class, ReviewDto.class)
                .setPostConverter (converter);
    }

    @Override
    public void mapSpecificFields (Review review, ReviewDto dto) {
        final Long userId = review.getUserId ();
        User user = userService.fetchBy (userId);
        dto.setUser (user);
    }
}
