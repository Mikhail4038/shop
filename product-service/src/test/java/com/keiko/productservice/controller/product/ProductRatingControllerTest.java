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

@WebMvcTest (ProductRatingController.class)
class ProductRatingControllerTest extends ParentProductControllerTest {
    private static final Float RATING = 5.2F;

    @Test
    void findProductsRatingLess_should_successfully () throws Exception {
        when (productService.findProductsRatingLessThan (RATING, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + RATING_BASE + RATING_LESS_THAN)
                .queryParam ("rating", RATING.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findProductsRatingLessThan (anyFloat (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }

    @Test
    void findProductsRatingMore_should_successfully () throws Exception {
        when (productService.findProductsRatingMoreThan (RATING, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + RATING_BASE + RATING_MORE_THAN)
                .queryParam ("rating", RATING.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findProductsRatingMoreThan (anyFloat (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }

    @Test
    void findProductsRatingRange_should_successfully () throws Exception {
        when (productService.findProductsRatingRange (MIN_RATING, MAX_RATING, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + RATING_BASE + RATING_RANGE)
                .queryParam ("minRating", MIN_RATING.toString ())
                .queryParam ("maxRating", MAX_RATING.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findProductsRatingRange (anyFloat (), anyFloat (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }
}
