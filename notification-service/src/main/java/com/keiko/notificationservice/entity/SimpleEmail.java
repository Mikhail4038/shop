package com.keiko.notificationservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleEmail {
    private String toAddress;
    private String subject;
    private String message;
}
