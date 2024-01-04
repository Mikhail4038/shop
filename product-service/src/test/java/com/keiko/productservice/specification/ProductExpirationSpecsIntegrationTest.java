package com.keiko.productservice.specification;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.keiko.productservice.repository.specs.ProductExpirationSpecs.isExpired;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductExpirationSpecsIntegrationTest extends ParentSpecsIntegrationTest {

    private Product jersey;
    private Product shorts;

    @Test
    void whenFindExpiredProduct_thenReturnProducts () {
        jersey = jersey ();
        shorts = shorts ();

        List<Product> products = productRepository.findAll (isExpired ());

        assertFalse (products.isEmpty ());
        assertFalse (products.contains (shorts));
        assertTrue (products.contains (jersey));
    }
}
