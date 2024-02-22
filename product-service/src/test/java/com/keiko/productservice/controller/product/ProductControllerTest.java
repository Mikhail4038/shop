package com.keiko.productservice.controller.product;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Arrays;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.PRODUCT_BASE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.BY_EAN;
import static com.keiko.productservice.constants.WebResourceKeyConstants.SEARCH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ProductController.class)
class ProductControllerTest extends ParentProductControllerTest {

    private static final Long PRODUCER_ID = 1L;
    private static final Boolean IS_PROMOTIONAL = false;

    @Test
    void search_should_successfully () throws Exception {
        when (productService.searchProducts (PRODUCER_ID, IS_PROMOTIONAL, MIN_PRICE, MAX_PRICE, MIN_RATING, MAX_RATING))
                .thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + SEARCH)
                .queryParam ("producerId", PRODUCER_ID.toString ())
                .queryParam ("isPromotional", IS_PROMOTIONAL.toString ())
                .queryParam ("minPrice", MIN_PRICE.toString ())
                .queryParam ("maxPrice", MAX_PRICE.toString ())
                .queryParam ("minRating", MIN_RATING.toString ())
                .queryParam ("maxRating", MAX_RATING.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).searchProducts (anyLong (), anyBoolean (), anyDouble (), anyDouble (), anyFloat (), anyFloat ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }

    @Test
    void findByEan_should_successfully () throws Exception {
        when (productService.findByEan (product.getEan ())).thenReturn (product);
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + BY_EAN)
                .contentType (APPLICATION_JSON_VALUE)
                .queryParam ("ean", product.getEan ()))
                .andExpect (jsonPath ("$.id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$.ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$.name", is (productDto.getName ())))
                .andExpect (status ().isOk ());
    }
}
