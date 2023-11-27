package com.keiko.productservice.dto.model.producer;

import com.keiko.productservice.dto.model.product.ProductData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProducerDto extends ProducerData {
    private List<ProductData> products;
}
