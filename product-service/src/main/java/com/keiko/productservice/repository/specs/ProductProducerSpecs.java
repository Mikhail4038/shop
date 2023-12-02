package com.keiko.productservice.repository.specs;

import com.keiko.productservice.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import static com.keiko.productservice.repository.specs.ProductPriceSpecs.setUpSortByPriceValue;
import static java.util.Objects.nonNull;

public class ProductProducerSpecs {
    public static Specification<Product> equalsProducer (Long producerId, Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Producer> productProducer = root.join (Product_.PRODUCER);
            if (nonNull (sortByAscend)) {
                Join<Product, Price> productPrice = root.join (Product_.PRICE);
                setUpSortByPriceValue (query, builder, productPrice, sortByAscend);
            }
            return builder.equal (productProducer.get (Producer_.ID), producerId);
        };
    }
}
