package com.keiko.shopservice.dto.converter.productStock;

import com.keiko.commonservice.dto.converter.AbstractToDtoConverter;
import com.keiko.commonservice.entity.resource.product.Product;
import com.keiko.shopservice.dto.model.productStock.ProductStockDto;
import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.service.resources.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductStockToDtoConverter
        extends AbstractToDtoConverter<ProductStock, ProductStockDto> {

    @Autowired
    private ProductService productService;

    public ProductStockToDtoConverter () {
        super (ProductStock.class, ProductStockDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (ProductStock.class, ProductStockDto.class)
                .setPostConverter (converter);
    }

    @Override
    public void mapSpecificFields (ProductStock entity, ProductStockDto dto) {
        String ean = entity.getEan ();
        Product product = productService.findByEan (ean);
        dto.setProduct (product);
    }
}
