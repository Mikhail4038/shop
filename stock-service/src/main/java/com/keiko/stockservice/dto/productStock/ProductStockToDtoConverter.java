package com.keiko.stockservice.dto.productStock;

import com.keiko.stockservice.dto.AbstractToDtoConverter;
import com.keiko.stockservice.entity.resources.Product;
import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.service.resources.ProductService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
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
                .addMappings (mapper -> mapper.skip (ProductStockDto::setProduct))
                .setPostConverter (converter);
    }

    Converter<ProductStock, ProductStockDto> converter = (context) -> {
        ProductStock entity = context.getSource ();
        ProductStockDto dto = context.getDestination ();
        mapSpecificFields (entity, dto);
        return dto;
    };

    @Override
    public void mapSpecificFields (ProductStock entity, ProductStockDto dto) {
        String ean = entity.getEan ();
        Product product = productService.findByEan (ean);
        dto.setProduct (product);
    }
}
