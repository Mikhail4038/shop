package com.keiko.productservice.controller.product;

import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.function.Function;

import static com.keiko.productservice.util.TestData.testProduct;
import static com.keiko.productservice.util.TestData.testProductDto;

class ParentProductControllerTest {

    protected static final Double MIN_PRICE = 10.2;
    protected static final Double MAX_PRICE = 45.8;
    protected static final Float MIN_RATING = 2.2F;
    protected static final Float MAX_RATING = 9.9F;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ProductServiceImpl productService;

    @MockBean
    protected Function<Product, ProductDto> toDtoConverter;

    @MockBean
    protected Function<ProductDto, Product> toEntityConverter;

    protected static Product product;
    protected static ProductDto productDto;

    @BeforeAll
    static void setUp () {
        product = getProduct ();
        productDto = getProductDto ();
    }

    protected static Product getProduct () {
        return testProduct ();
    }

    protected static ProductDto getProductDto () {
        return testProductDto ();
    }
}
