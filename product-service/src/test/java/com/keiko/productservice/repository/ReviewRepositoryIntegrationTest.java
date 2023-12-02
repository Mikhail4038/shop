package com.keiko.productservice.repository;

import com.keiko.productservice.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@DataJpaTest
class ReviewRepositoryIntegrationTest {

    private static final Long USER_ID = 1L;
    private static final Long PRODUCT_ID = 1L;

    @Autowired
    private ReviewRepository reviewRepository;

    //@Test
    void whenFindByUserId_thenReturnReviews () {
        List<Review> reviews = reviewRepository.findByUser (USER_ID);
        assertFalse (reviews.isEmpty ());
        assertTrue (reviews.size () == 2);
    }

   // @Test
    void whenFindByProductId_thenReturnReviews () {
        List<Review> reviews = reviewRepository.findByProduct_id (PRODUCT_ID);
        assertFalse (reviews.isEmpty ());
        assertTrue (reviews.size () == 1);
    }
}
