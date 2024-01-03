package com.keiko.productservice.service;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.repository.ReviewRepository;
import com.keiko.productservice.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.keiko.productservice.util.TestData.testProduct;
import static com.keiko.productservice.util.TestData.testReview;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class ReviewServiceUnitTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private static ReviewServiceImpl reviewService;

    private static Review review;
    private static Product product;

    @BeforeAll
    static void setUp () {
        reviewService = new ReviewServiceImpl ();
        review = testReview ();
        product = testProduct ();
        review.setProduct (product);
    }

    @Test
    void should_successfully_get_user_reviews () {
        final Long userId = review.getUserId ();
        when (reviewRepository.findByUserId (userId)).thenReturn (Arrays.asList (review));

        List<Review> reviews = reviewService.getUserReviews (userId);

        assertFalse (reviews.isEmpty ());
        assertEquals (reviews.size (), 1);
        assertTrue (reviews.contains (review));

        verify (reviewRepository, times (1)).findByUserId (anyLong ());
        verifyNoMoreInteractions (reviewRepository);
    }

    @Test
    void should_successfully_get_product_reviews () {
        final Long productId = review.getProduct ().getId ();
        when (reviewRepository.findByProduct_id (productId)).thenReturn (Arrays.asList (review));

        List<Review> reviews = reviewService.getProductReviews (productId);

        assertFalse (reviews.isEmpty ());
        assertEquals (reviews.size (), 1);
        assertTrue (reviews.contains (review));

        verify (reviewRepository, times (1)).findByProduct_id (anyLong ());
        verifyNoMoreInteractions (reviewRepository);
    }
}
