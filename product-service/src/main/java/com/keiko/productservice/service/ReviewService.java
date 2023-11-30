package com.keiko.productservice.service;

import com.keiko.productservice.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getUserReviews (Long userId);

    List<Review> getProductReviews (Long productId);
}
