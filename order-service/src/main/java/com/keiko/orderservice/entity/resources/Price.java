package com.keiko.orderservice.entity.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Price {
    private Double value;
    private boolean isPromotional;
}
