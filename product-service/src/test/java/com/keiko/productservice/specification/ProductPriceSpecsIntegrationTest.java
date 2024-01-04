package com.keiko.productservice.specification;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.keiko.productservice.repository.specs.ProductPriceSpecs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class ProductPriceSpecsIntegrationTest extends ParentSpecsIntegrationTest {

    private Product jersey;
    private Product shorts;

    @Test
    void whenFindProductsByPriceLessThan_thenReturnProducts () {
        jersey = jersey ();
        shorts = shorts ();

        List<Product> products = productRepository.findAll (
                hasPriceOfLessThan (70.0, true));

        assertFalse (products.isEmpty ());
        assertEquals (products.size (), 2);
        assertEquals (products.get (0), shorts);
        assertEquals (products.get (1), jersey);
    }

    @Test
    void whenFindProductsByPriceMoreThan_thenReturnProducts () {
        jersey = jersey ();
        shorts = shorts ();

        List<Product> products = productRepository.findAll (
                hasPriceOfMoreThan (40.0, true));

        assertFalse (products.isEmpty ());
        assertEquals (products.size (), 1);
        assertEquals (products.get (0), jersey);
    }

    @Test
    void whenFindProductsByPriceBetween_thenReturnProducts () {
        jersey = jersey ();
        shorts = shorts ();

        List<Product> products = productRepository.findAll (
                hasPriceBetween (30.0, 80.0, false));

        assertFalse (products.isEmpty ());
        assertEquals (products.size (), 2);
        assertEquals (products.get (0), jersey);
        assertEquals (products.get (1), shorts);
    }
}
