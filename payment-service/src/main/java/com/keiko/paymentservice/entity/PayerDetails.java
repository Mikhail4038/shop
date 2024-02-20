package com.keiko.paymentservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PayerDetails {
    private String id;
    private String name;
    private String email;
}
