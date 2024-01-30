package com.keiko.userservice.exception.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String errorClass;
    private String errorMessage;
}
