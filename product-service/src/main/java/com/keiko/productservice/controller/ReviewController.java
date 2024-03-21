package com.keiko.productservice.controller;

import com.keiko.commonservice.controller.DefaultCrudController;
import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.Review;
import com.keiko.productservice.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag (name = "Reviews API")
public class ReviewController extends DefaultCrudController<Review, ReviewDto> {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private Function<Review, ReviewData> toDataConverter;

    @GetMapping (value = USER_REVIEWS)
    public ResponseEntity<List<ReviewDto>> getUserReviews (@RequestParam Long userId) {
        List<Review> reviews = reviewService.getUserReviews (userId);
        List<ReviewDto> dto = toDtoConvert (reviews);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = PRODUCT_REVIEWS)
    public ResponseEntity<List<ReviewData>> getProductReviews (@RequestParam Long productId) {
        List<Review> reviews = reviewService.getProductReviews (productId);
        List<ReviewData> dto = toDataConvert (reviews);
        return ResponseEntity.ok (dto);
    }

    private List<ReviewDto> toDtoConvert (List<Review> reviews) {
        return reviews.stream ()
                .map (getToDtoConverter ()::apply)
                .collect (toList ());
    }

    private List<ReviewData> toDataConvert (List<Review> reviews) {
        return reviews.stream ()
                .map (toDataConverter::apply)
                .collect (toList ());
    }
}
