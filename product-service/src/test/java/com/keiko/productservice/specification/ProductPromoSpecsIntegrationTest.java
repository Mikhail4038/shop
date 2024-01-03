package com.keiko.productservice.specification;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.keiko.productservice.repository.specs.ProductPromoSpecs.isPromotionalPrice;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductPromoSpecsIntegrationTest extends ParentSpecsIntegrationTest {

    private Product jersey;

    @Test
    void whenFindPromoProducts_thenReturnProducts () {
        jersey = getJersey ();

        List<Product> products = productRepository.findAll (
                isPromotionalPrice (true));

        assertFalse (products.isEmpty ());
        assertEquals (products.size (), 1);
        assertTrue (products.contains (jersey));
    }
}
