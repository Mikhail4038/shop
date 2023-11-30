package com.keiko.productservice.service.impl;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.OnDeleteReviewCompleteEvent;
import com.keiko.productservice.event.OnSaveReviewCompleteEvent;
import com.keiko.productservice.repository.ReviewRepository;
import com.keiko.productservice.service.CrudService;
import com.keiko.productservice.service.ReviewService;
import com.keiko.productservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ReviewServiceImpl extends CrudServiceImpl<Review> implements ReviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private CrudService<Product> productService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void save (Review review) {
        if (isNull (review.getId ())) {
            final Long id = review.getUser ();
            // TODO
            //userService.fetchBy (id);
            eventPublisher.publishEvent (new OnSaveReviewCompleteEvent (review));
        }
        super.save (review);
    }

    @Override
    @Transactional
    public void delete (Long id) {
        Review review = this.fetchBy (id);
        super.delete (id);
        eventPublisher.publishEvent (new OnDeleteReviewCompleteEvent (review));
    }

    @Override
    public List<Review> getUserReviews (Long userId) {
        return reviewRepository.findByUser (userId);
    }

    @Override
    public List<Review> getProductReviews (Long productId) {
        return reviewRepository.findByProduct_id (productId);
    }
}
