package com.keiko.productservice.specification;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.keiko.productservice.repository.specs.ProductRatingSpecs.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRatingSpecsIntegrationTest extends ParentSpecsIntegrationTest {

    private Product jersey;
    private Product shorts;

    @Test
    void whenFindProductsByRatingLess_thenReturnProducts () {
        jersey = jersey ();
        shorts = shorts ();

        List<Product> products = productRepository.findAll (
                hasRatingOfLessThan (6.0F, null));

        assertFalse (products.isEmpty ());
        assertFalse (products.contains (shorts));
        assertTrue (products.contains (jersey));
    }

    @Test
    void whenFindProductsByRatingMore_thenReturnProducts () {
        jersey = jersey ();
        shorts = shorts ();

        List<Product> products = productRepository.findAll (
                hasRatingOfMoreThan (6.0F, null));

        assertFalse (products.isEmpty ());
        assertFalse (products.contains (jersey));
        assertTrue (products.contains (shorts));
    }

    @Test
    void whenFindProductsByRatingBetween_thenReturnProducts () {
        jersey = jersey ();
        shorts = shorts ();

        List<Product> products = productRepository.findAll (
                hasRatingBetween (4.0F, 11.0F, true));

        assertFalse (products.isEmpty ());
        assertEquals (products.size (), 2);
        assertEquals (products.get (0), jersey);
        assertEquals (products.get (1), shorts);
    }
}
