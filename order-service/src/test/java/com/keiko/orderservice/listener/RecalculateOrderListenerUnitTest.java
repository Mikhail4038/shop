package com.keiko.orderservice.listener;

import com.keiko.commonservice.entity.resource.product.Product;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.event.listener.RecalculateOrderListener;
import com.keiko.orderservice.service.resources.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.keiko.orderservice.util.TestData.testProduct;
import static com.keiko.orderservice.util.TestData.testRecalculateOrderEvent;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class RecalculateOrderListenerUnitTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private RecalculateOrderListener recalculateOrderListener;

    private RecalculateOrderEvent recalculateOrderEvent;
    private Product product;

    @Test
    void should_successfully_recalculateOrder () {
        recalculateOrderEvent = testRecalculateOrderEvent ();
        product = testProduct ();
        String ean = recalculateOrderEvent.getOrder ().getEntries ().get (0).getProductEan ();

        when (productService.findByEan (ean)).thenReturn (product);

        recalculateOrderListener.onApplicationEvent (recalculateOrderEvent);

        verify (productService, times (1)).findByEan (anyString ());
    }
}
