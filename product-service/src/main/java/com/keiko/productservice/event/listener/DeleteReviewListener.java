package com.keiko.productservice.event.listener;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Rating;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.AfterDeletedReviewEvent;
import com.keiko.productservice.service.CrudService;
import com.keiko.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteReviewListener
        implements ApplicationListener<AfterDeletedReviewEvent> {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CrudService<Product> productService;

    @Override
    public void onApplicationEvent (AfterDeletedReviewEvent event) {
        final Review review = event.getReview ();
        final byte assessment = review.getAssessment ();
        final Long productId = review.getProduct ().getId ();
        final Product product = productService.fetchBy (productId);

        Rating newRating = calculateProductRating (productId, assessment);
        Rating actualRating = product.getRating ();
        actualRating.setAverageAssessment (newRating.getAverageAssessment ());
        actualRating.setCountReviews (newRating.getCountReviews ());
        product.setRating (actualRating);
        productService.save (product);
    }

    private Rating calculateProductRating (Long productId, byte assessment) {
        List<Review> reviews = reviewService.getProductReviews (productId);
        if (reviews.isEmpty ()) {
            return new Rating (0f, 0);
        }

        Double sum = reviews.stream ().mapToDouble (Review::getAssessment).sum ();
        int count = reviews.size ();
        Double averageAssessment = sum / count;
        Rating rating = new Rating (averageAssessment.floatValue (), count);
        return rating;
    }
}
