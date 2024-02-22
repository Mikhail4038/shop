package com.keiko.commonservice.entity.resource.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Price {
    private BigDecimal value;
    private boolean isPromotional;
}
