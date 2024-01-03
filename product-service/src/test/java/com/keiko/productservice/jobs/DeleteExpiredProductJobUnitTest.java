package com.keiko.productservice.jobs;

import com.keiko.productservice.service.product.ProductExpirationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith (MockitoExtension.class)
class DeleteExpiredProductJobUnitTest {

    @Mock
    private ProductExpirationService productService;

    @InjectMocks
    private DeleteExpiredProductJob job;

    @Test
    void should_successfully_deleteExpiredProducts () {
        job.deleteExpiredProducts ();
        verify (productService, times (1)).deleteExpiredProduct ();
    }
}
