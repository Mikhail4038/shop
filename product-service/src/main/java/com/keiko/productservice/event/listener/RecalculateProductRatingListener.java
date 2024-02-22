package com.keiko.productservice.event.listener;

import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Rating;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.RecalculateProductRatingEvent;
import com.keiko.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
public class RecalculateProductRatingListener {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DefaultCrudService<Product> productService;

    @EventListener
    public void onApplicationEvent (RecalculateProductRatingEvent event) {
        final Review review = event.getReview ();
        final Long productId = review.getProduct ().getId ();
        final Product product = productService.fetchBy (productId);

        Rating newRating = recalculateProductRating (productId);
        Rating actualRating = product.getRating ();
        if (isNull (actualRating)) {
            actualRating = new Rating ();
        }
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
