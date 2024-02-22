package com.keiko.productservice.service.impl;

import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.RecalculateProductRatingEvent;
import com.keiko.productservice.repository.ReviewRepository;
import com.keiko.productservice.service.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl extends DefaultCrudServiceImpl<Review> implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void save (Review review) {
        super.save (review);
        eventPublisher.publishEvent (new RecalculateProductRatingEvent (review));
    }

    @Override
    @Transactional
    public void delete (Long id) {
        Review review = this.fetchBy (id);
        super.delete (id);
        eventPublisher.publishEvent (new RecalculateProductRatingEvent (review));
    }

    @Override
    public List<Review> getUserReviews (Long userId) {
        return reviewRepository.findByUserId (userId);
    }

    @Override
    public List<Review> getProductReviews (Long productId) {
        return reviewRepository.findByProduct_id (productId);
    }
}
