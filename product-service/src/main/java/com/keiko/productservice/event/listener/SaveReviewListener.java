package com.keiko.productservice.event.listener;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Rating;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.OnSaveReviewCompleteEvent;
import com.keiko.productservice.service.CrudService;
import com.keiko.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaveReviewListener
        implements ApplicationListener<OnSaveReviewCompleteEvent> {

    @Autowired
    private CrudService<Rating> ratingService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CrudService<Product> productService;

    @Override
    public void onApplicationEvent (OnSaveReviewCompleteEvent event) {
        final Review review = event.getReview ();
        final float presentedAssessment = review.getAssessment ();
        final Long productId = review.getProduct ().getId ();
        final Product product = productService.fetchBy (productId);

        Rating actualRating = product.getRating ();
        Rating newRating = calculateProductRating (productId, presentedAssessment);

        actualRating.setAverageAssessment (newRating.getAverageAssessment ());
        actualRating.setCountReviews (newRating.getCountReviews ());
        product.setRating (actualRating);
        productService.save (product);
    }

    private Rating calculateProductRating (Long productId, float assessment) {

        List<Review> reviews = reviewService.getProductReviews (productId);
        int countAssessment = reviews.size ();
        Double sum = reviews.stream ().mapToDouble (Review::getAssessment).sum ();
        sum += assessment;
        countAssessment++;
        Double averageAssessment = sum / countAssessment;
        return new Rating (averageAssessment.floatValue (), countAssessment);
    }
}
