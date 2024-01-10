package com.keiko.stockservice.dto;

import com.keiko.stockservice.entity.Product;
import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ToDtoConverter
        implements Function<ProductStock, ProductStockDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void setUpMapping () {
        modelMapper.createTypeMap (ProductStock.class, ProductStockDto.class)
                .addMappings (mapper -> mapper.skip (ProductStockDto::setProduct))
                .setPostConverter (converter);
    }

    @Override
    public ProductStockDto apply (ProductStock productStock) {
        return modelMapper.map (productStock, ProductStockDto.class);
    }

    Converter<ProductStock, ProductStockDto> converter = (context) -> {
        ProductStock entity = context.getSource ();
        ProductStockDto dto = context.getDestination ();
        mapSpecificFields (entity, dto);
        return dto;
    };

    private void mapSpecificFields (ProductStock entity, ProductStockDto dto) {
        String ean = entity.getEan ();
        Product product = productService.findByEan (ean);
        dto.setProduct (product);
    }
}
