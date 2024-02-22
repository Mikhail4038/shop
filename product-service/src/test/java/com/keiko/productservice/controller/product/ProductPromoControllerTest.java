package com.keiko.productservice.controller.product;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Arrays;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.PRODUCT_BASE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.PROMO_BASE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.PROMO_PRODUCTS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ProductPromoController.class)
class ProductPromoControllerTest extends ParentProductControllerTest {

    @Test
    void findPromoProducts_should_successfully () throws Exception {

        when (productService.findPromoProducts (null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + PROMO_BASE + PROMO_PRODUCTS)
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findPromoProducts (any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));

    }
}
