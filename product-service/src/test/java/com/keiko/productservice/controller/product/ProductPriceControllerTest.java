package com.keiko.productservice.controller.product;

import com.keiko.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Arrays;

import static com.keiko.productservice.constants.WebResourceKeyConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ProductPriceController.class)
class ProductPriceControllerTest extends ParentProductControllerTest {

    private static final Double PRICE = 10.0;

    @Test
    void findProductsPriceLess_should_successfully () throws Exception {

        when (productService.findProductsPriceLessThan (PRICE, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + PRICE_BASE + PRICE_LESS_THAN)
                .queryParam ("price", PRICE.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findProductsPriceLessThan (anyDouble (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }

    @Test
    void findProductsPriceMore_should_successfully () throws Exception {
        when (productService.findProductsPriceMoreThan (PRICE, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + PRICE_BASE + PRICE_MORE_THAN)
                .queryParam ("price", PRICE.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findProductsPriceMoreThan (anyDouble (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }

    @Test
    void findProductsPriceRange_should_successfully () throws Exception {
        when (productService.findProductsPriceRange (MIN_PRICE, MAX_PRICE, null)).thenReturn (Arrays.asList (product));
        when (toDtoConverter.apply (product)).thenReturn (productDto);

        mockMvc.perform (get (PRODUCT_BASE + PRICE_BASE + PRICE_RANGE)
                .queryParam ("minPrice", MIN_PRICE.toString ())
                .queryParam ("maxPrice", MAX_PRICE.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].id", is (productDto.getId ()), Long.class))
                .andExpect (jsonPath ("$[0].ean", is (productDto.getEan ())))
                .andExpect (jsonPath ("$[0].name", is (productDto.getName ())))
                .andExpect (status ().isOk ());

        verify (productService, times (1)).findProductsPriceRange (anyDouble (), anyDouble (), any ());
        verify (toDtoConverter, times (1)).apply (any (Product.class));
    }
}
