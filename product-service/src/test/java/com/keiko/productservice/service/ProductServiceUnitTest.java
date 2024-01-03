package com.keiko.productservice.service;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.exception.model.ProductNotFoundException;
import com.keiko.productservice.repository.ProductRepository;
import com.keiko.productservice.service.product.ProductService;
import com.keiko.productservice.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static com.keiko.productservice.util.TestData.testProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class ProductServiceUnitTest {

    private static final String EXPECTED_EXCEPTION_MESSAGE = "Product with ean: %s not found";

    private static final Long PRODUCER_ID = 1L;
    private static final Boolean IS_PROMOTIONAL = true;
    private static final Double MIN_PRICE = 10.0;
    private static final Double MAX_PRICE = 20.0;
    private static final Float MIN_RATING = 1.0F;
    private static final Float MAX_RATING = 10.0F;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Specification specification;

    @InjectMocks
    private static ProductService productService;

    private static Product product;

    @BeforeAll
    static void setUp () {
        productService = new ProductServiceImpl ();
        product = testProduct ();
    }

    @Test
    void should_successfully_findByEan () {
        String ean = product.getEan ();
        when (productRepository.findByEan (ean)).thenReturn (Optional.of (product));

        Product result = productService.findByEan (ean);

        assertEquals (product, result);
        verify (productRepository, times (1)).findByEan (anyString ());
        verifyNoMoreInteractions (productRepository);
    }

    @Test
    void should_unSuccessfully_findByEan_notFound () {
        String notSavesEan = "147";
        String exceptionMessage = String.format (EXPECTED_EXCEPTION_MESSAGE, notSavesEan);

        Exception exception = assertThrows (ProductNotFoundException.class,
                () -> productService.findByEan (notSavesEan));

        assertEquals (exception.getMessage (), exceptionMessage);
    }

    @Test
    void should_successfully_search () {
        productService.searchProducts (PRODUCER_ID, IS_PROMOTIONAL, MIN_PRICE, MAX_PRICE, MIN_RATING, MAX_RATING);
        verify (productRepository, times (1)).findAll (any (Specification.class));
    }
}
