package com.keiko.productservice.dto.model.rating;

import com.keiko.productservice.dto.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto extends BaseDto {
    private Float value;
    private Integer countReviews;
}
