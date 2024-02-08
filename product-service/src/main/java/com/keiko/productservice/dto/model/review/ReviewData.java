package com.keiko.productservice.dto.model.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keiko.productservice.dto.model.BaseDto;
import com.keiko.productservice.entity.resources.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReviewData extends BaseDto {

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp modified;

    private User user;
    private String message;
    private Integer assessment;
}
