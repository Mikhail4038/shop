package com.keiko.productservice.repository;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class ProductRepositoryIntegrationTest {

    private static final String PRODUCT_EAN = "123";
    private static final String NOT_SAVED_PRODUCT_EAN = "unknown";
    private static final String EXPECTED_EXCEPTION_MESSAGE = "No value present";

    @Autowired
    private ProductRepository productRepository;

    @Test
    void whenFindByEan_thenReturnProduct () {
        Product product = productRepository.findByEan (PRODUCT_EAN).get ();
        assertEquals (product.getEan (), PRODUCT_EAN);
    }

    @Test
    void whenFindByNotSavesEan_thenReturnException () {
        Exception exception = assertThrows (NoSuchElementException.class,
                () -> productRepository.findByEan (NOT_SAVED_PRODUCT_EAN).get ());
        assertEquals (exception.getMessage (), EXPECTED_EXCEPTION_MESSAGE);
    }
}
