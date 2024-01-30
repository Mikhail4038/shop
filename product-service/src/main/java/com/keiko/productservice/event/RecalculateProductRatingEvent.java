package com.keiko.productservice.event;

import com.keiko.productservice.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecalculateProductRatingEvent {
    private Review review;

    public RecalculateProductRatingEvent (Review review) {
        this.review = review;
    }
}
