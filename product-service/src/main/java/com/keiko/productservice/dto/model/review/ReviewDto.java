package com.keiko.productservice.dto.model.review;

import com.keiko.productservice.dto.model.product.ProductData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto extends ReviewData {
    private ProductData product;
}
