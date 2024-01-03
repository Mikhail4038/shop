package com.keiko.productservice.jobs;

import com.keiko.productservice.entity.Price;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.keiko.productservice.util.TestData.testPrice;
import static com.keiko.productservice.util.TestData.testProduct;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class SetUpPromotionJobUnitTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private SetUpPromotionJob job;

    private static Product product;
    private static Price price;

    @BeforeAll
    static void setUp () {
        product = testProduct ();
        price = testPrice ();
        product.setPrice (price);
    }

    @Test
    void should_successfully_setUpPromotion () {
        when (productService.findProductExpirationDateSoon (anyByte ())).thenReturn (Arrays.asList (product));
        job.setUpPromotion ();
        verify (productService, times (1)).findProductExpirationDateSoon (anyByte ());
        verify (productService, times (1)).save (any (Product.class));

        InOrder orderProductService = Mockito.inOrder (productService);
        orderProductService.verify (productService).findProductExpirationDateSoon (anyByte ());
        orderProductService.verify (productService).save (any (Product.class));
    }
}
