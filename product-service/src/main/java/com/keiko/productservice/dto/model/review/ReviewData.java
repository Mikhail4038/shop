package com.keiko.productservice.dto.model.review;

import com.keiko.productservice.dto.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewData extends BaseDto {
    private Long user;
    private String message;
    private byte assessment;
}
