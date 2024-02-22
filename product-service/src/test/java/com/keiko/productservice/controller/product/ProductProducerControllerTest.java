package com.keiko.productservice.controller.product;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Arrays;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.PRODUCT_BASE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ProductProducerController.class)
class ProductProducerControllerTest extends ParentProductControllerTest {

    private static final Long PRODUCER_ID = 1L;

    @Test
    void findByProducer_should_successfully () throws Exception {

        when (productService.findProductsByProducer (PRODUCER_ID, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + PRODUCT_PRODUCER_BASE + BY_PRODUCER)
                .queryParam ("producerId", PRODUCER_ID.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findProductsByProducer (anyLong (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }

    @Test
    void findPromoProductByProducer_should_successfully () throws Exception {
        when (productService.findPromoProductByProducer (PRODUCER_ID, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + PRODUCT_PRODUCER_BASE + PROMO_BY_PRODUCER)
                .queryParam ("producerId", PRODUCER_ID.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findPromoProductByProducer (anyLong (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }
}
