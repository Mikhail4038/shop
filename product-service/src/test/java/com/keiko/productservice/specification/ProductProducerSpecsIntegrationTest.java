package com.keiko.productservice.specification;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.keiko.productservice.repository.specs.ProductProducerSpecs.equalsProducer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class ProductProducerSpecsIntegrationTest extends ParentSpecsIntegrationTest {

    private Product jersey;
    private Product shorts;

    @Test
    void whenFindProductsByProducer_thenReturnProducts () {
        jersey = getJersey ();
        shorts = getShorts ();

        List<Product> products = productRepository.findAll (
                equalsProducer (1L, false));

        assertFalse (products.isEmpty ());
        assertEquals (products.size (), 2);
        assertEquals (products.get (0), jersey);
        assertEquals (products.get (1), shorts);
    }
}
