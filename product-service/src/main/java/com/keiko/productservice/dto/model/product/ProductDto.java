package com.keiko.productservice.dto.model.product;

import com.keiko.productservice.dto.model.producer.ProducerData;
import com.keiko.productservice.dto.model.review.ReviewData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto extends ProductData {
    private List<ReviewData> reviews;
    private ProducerData producer;
}
