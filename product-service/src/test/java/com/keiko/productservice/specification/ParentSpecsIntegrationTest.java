package com.keiko.productservice.specification;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.repository.ProductRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class ParentSpecsIntegrationTest {

    private static final String JERSEY_EAN = "123";
    private static final String SHORTS_EAN = "456";

    @Autowired
    protected ProductRepository productRepository;

    protected Product jersey () {
        return productRepository.findByEan (JERSEY_EAN).get ();
    }

    protected Product shorts () {
        return productRepository.findByEan (SHORTS_EAN).get ();
    }
}
