package com.keiko.productservice.controller;

import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

import static com.keiko.productservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping (value = REVIEW_BASE)
public class ReviewController extends CrudController<Review, ReviewDto> {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private Function<Review, ReviewData> toDtoConverter;

    @GetMapping (value = USER_REVIEWS)
    public ResponseEntity<List<ReviewData>> getUserReviews (@RequestParam Long userId) {
        List<Review> reviews = reviewService.getUserReviews (userId);
        List<ReviewData> dto = toDtoConvert (reviews);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = PRODUCT_REVIEWS)
    public ResponseEntity<List<ReviewData>> getProductReviews (@RequestParam Long productId) {
        List<Review> reviews = reviewService.getProductReviews (productId);
        List<ReviewData> dto = toDtoConvert (reviews);
        return ResponseEntity.ok (dto);
    }

    private List<ReviewData> toDtoConvert (List<Review> reviews) {
        return reviews.stream ()
                .map (toDtoConverter::apply)
                .collect (toList ());
    }
}
