package com.keiko.productservice.event.listener;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Rating;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.RecalculateProductRatingEvent;
import com.keiko.productservice.service.CrudService;
import com.keiko.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecalculateProductRatingListener
        implements ApplicationListener<RecalculateProductRatingEvent> {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CrudService<Product> productService;

    @Override
    public void onApplicationEvent (RecalculateProductRatingEvent event) {
        final Review review = event.getReview ();
        final Product product = review.getProduct ();
        final Long productId = product.getId ();

        Rating newRating = recalculateProductRating (productId);
        Rating actualRating = product.getRating ();
        actualRating.setValue (newRating.getValue ());
        actualRating.setCountReviews (newRating.getCountReviews ());
        product.setRating (actualRating);
        productService.save (product);
    }

    private Rating recalculateProductRating (Long productId) {
        List<Review> reviews = reviewService.getProductReviews (productId);
        if (reviews.isEmpty ()) {
            return new Rating (0f, 0);
        }
        int countAssessment = reviews.size ();

        Double averageAssessment = reviews.stream ()
                .mapToDouble (Review::getAssessment)
                .summaryStatistics ().getAverage ();

        Rating rating = new Rating (averageAssessment.floatValue (), countAssessment);
        return rating;
    }
}
