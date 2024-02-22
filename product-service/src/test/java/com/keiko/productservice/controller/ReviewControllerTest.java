package com.keiko.productservice.controller;

import com.keiko.commonservice.entity.resource.User;
import com.keiko.productservice.dto.model.product.ProductData;
import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.function.Function;

import static com.keiko.productservice.constants.WebResourceKeyConstants.*;
import static com.keiko.productservice.util.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewServiceImpl reviewService;

    @MockBean
    private Function<Review, ReviewData> toDataConverter;

    @MockBean
    private Function<Review, ReviewDto> toDtoConverter;

    @MockBean
    private Function<ReviewDto, Review> toEntityConverter;

    private static Review review;
    private static User user;
    private static Product product;
    private static ProductData productData;
    private static ReviewDto reviewDto;
    private static ReviewData reviewData;

    @BeforeAll
    static void setUp () {
        review = testReview ();
        reviewDto = testReviewDto ();
        reviewData = testReviewData ();
        product = testProduct ();
        productData = testProductData ();
        user = testUser ();

        review.setProduct (product);
        reviewDto.setUser (user);
        reviewDto.setProduct (productData);
        reviewData.setUser (user);
    }

    @Test
    void getUserReviews_should_successfully () throws Exception {
        final Long userId = review.getUserId ();
        when (reviewService.getUserReviews (userId)).thenReturn (Arrays.asList (review));
        when (toDtoConverter.apply (review)).thenReturn (reviewDto);

        mockMvc.perform (get (REVIEW_BASE + USER_REVIEWS)
                .queryParam ("userId", userId.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))

                .andExpect (jsonPath ("$[0].user.id", is (reviewDto.getUser ().getId ()), Long.class))
                .andExpect (jsonPath ("$[0].user.email", is (reviewDto.getUser ().getEmail ())))
                .andExpect (jsonPath ("$[0].user.name", is (reviewDto.getUser ().getName ())))

                .andExpect (jsonPath ("$[0].message", is (review.getMessage ())))
                .andExpect (jsonPath ("$[0].assessment", is (review.getAssessment ()), Integer.class))

                .andExpect (jsonPath ("$[0].product.id", is (reviewDto.getProduct ().getId ()), Long.class))
                .andExpect (jsonPath ("$[0].product.ean", is (reviewDto.getProduct ().getEan ())))
                .andExpect (jsonPath ("$[0].product.name", is (reviewDto.getProduct ().getName ())))

                .andExpect (status ().isOk ());

        verify (reviewService, times (1)).getUserReviews (anyLong ());
        verify (toDtoConverter, times (1)).apply (any (Review.class));
    }

    @Test
    void getProductReviews_should_successfully () throws Exception {
        final Long productId = review.getProduct ().getId ();
        when (reviewService.getProductReviews (productId)).thenReturn (Arrays.asList (review));
        when (toDataConverter.apply (review)).thenReturn (reviewData);

        mockMvc.perform (get (REVIEW_BASE + PRODUCT_REVIEWS)
                .contentType (APPLICATION_JSON_VALUE)
                .queryParam ("productId", productId.toString ()))
                .andExpect (jsonPath ("$", hasSize (1)))

                .andExpect (jsonPath ("$[0].user.id", is (reviewData.getUser ().getId ()), Long.class))
                .andExpect (jsonPath ("$[0].user.email", is (reviewData.getUser ().getEmail ())))
                .andExpect (jsonPath ("$[0].user.name", is (reviewData.getUser ().getName ())))

                .andExpect (jsonPath ("$[0].message", is (review.getMessage ())))
                .andExpect (jsonPath ("$[0].assessment", is (review.getAssessment ()), Integer.class))
                .andExpect (status ().isOk ());

        verify (reviewService, times (1)).getProductReviews (anyLong ());
        verify (toDataConverter, times (1)).apply (any (Review.class));
    }
}
