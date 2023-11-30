package com.keiko.productservice.event.listener;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Rating;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.OnDeleteReviewCompleteEvent;
import com.keiko.productservice.service.CrudService;
import com.keiko.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Component
public class DeleteReviewListener
        implements ApplicationListener<OnDeleteReviewCompleteEvent> {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CrudService<Product> productService;

    @Override
    public void onApplicationEvent (OnDeleteReviewCompleteEvent event) {
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
            return new Rating (0f,0);
        }

        int sum = reviews.stream ().mapToInt (Review::getAssessment).sum ();
        int count = reviews.size ();
        float averageAssessment = sum / count;
        MathContext context = new MathContext (2, RoundingMode.HALF_UP);
        Float result = new BigDecimal (averageAssessment, context).floatValue ();
        Rating rating = new Rating (result, count);
        return rating;
    }
}
