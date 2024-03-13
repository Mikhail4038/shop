package com.keiko.productservice.dto.model.review;

import com.keiko.commonservice.dto.model.BaseDto;
import com.keiko.commonservice.entity.resource.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewData extends BaseDto {
    private User user;
    private String message;
    private Integer assessment;
}
