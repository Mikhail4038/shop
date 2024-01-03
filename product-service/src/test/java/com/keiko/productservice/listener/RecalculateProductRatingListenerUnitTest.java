package com.keiko.productservice.listener;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Rating;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.event.RecalculateProductRatingEvent;
import com.keiko.productservice.event.listener.RecalculateProductRatingListener;
import com.keiko.productservice.service.CrudService;
import com.keiko.productservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static com.keiko.productservice.util.TestData.recalculateProductRatingEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class RecalculateProductRatingListenerUnitTest {

    @Mock
    private CrudService<Rating> ratingService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private CrudService<Product> productService;

    @InjectMocks
    private RecalculateProductRatingListener recalculateProductRatingListener;

    @Test
    void should_successfully_onApplicationEvent () {
        final RecalculateProductRatingEvent event = recalculateProductRatingEvent ();
        final Review review = event.getReview ();
        final Product product = review.getProduct ();
        final Long productId = product.getId ();

        when (reviewService.getProductReviews (productId)).thenReturn (Arrays.asList (review));

        recalculateProductRatingListener.onApplicationEvent (event);

        assertEquals (product.getRating ().getValue (), 5f);
        assertEquals (product.getRating ().getCountReviews (), 1);

        verify (reviewService, times (1)).getProductReviews (anyLong ());
        verify (productService, times (1)).save (any (Product.class));
    }

    @Test
    void should_successfully_onApplicationEvent_reviewsIsEmpty () {
        final RecalculateProductRatingEvent event = recalculateProductRatingEvent ();
        final Product product = event.getReview ().getProduct ();
        final Long productId = product.getId ();

        when (reviewService.getProductReviews (productId)).thenReturn (Collections.EMPTY_LIST);

        recalculateProductRatingListener.onApplicationEvent (event);

        assertEquals (product.getRating ().getValue (), 0f);
        assertEquals (product.getRating ().getCountReviews (), 0);

        verify (reviewService, times (1)).getProductReviews (anyLong ());
        verify (productService, times (1)).save (any (Product.class));
    }
}
