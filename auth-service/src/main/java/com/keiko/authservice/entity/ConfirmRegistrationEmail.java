package com.keiko.authservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfirmRegistrationEmail {
    private String toAddress;
    private String subject;
    private String message;
}
