package com.keiko.productservice.event;

import com.keiko.productservice.entity.Review;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnDeleteReviewCompleteEvent extends ApplicationEvent {
    private Review review;

    public OnDeleteReviewCompleteEvent (Review review) {
        super (review);
        this.review = review;
    }
}
