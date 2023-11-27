package com.keiko.productservice.service.impl;

import com.keiko.productservice.entity.Review;
import com.keiko.productservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends CrudServiceImpl<Review> {

    @Autowired
    private UserService userService;

    @Override
    public void save (Review review) {
        final Long id = review.getUser ();
        userService.fetchBy (id);
        super.save (review);
    }
}
