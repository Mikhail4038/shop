package com.keiko.productservice.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BaseDto {
    private Long id;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp modified;
}
