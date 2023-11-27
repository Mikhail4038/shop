package com.keiko.productservice.controller;

import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.Review;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.productservice.constants.WebResourceKeyConstants.REVIEW_BASE;

@RestController
@RequestMapping (value = REVIEW_BASE)
public class ReviewController extends CrudController<Review, ReviewDto> {
}
