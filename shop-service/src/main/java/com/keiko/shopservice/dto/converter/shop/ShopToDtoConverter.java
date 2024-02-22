package com.keiko.shopservice.dto.converter.shop;

import com.keiko.commonservice.dto.converter.AbstractToDtoConverter;
import com.keiko.shopservice.dto.model.productStock.ProductStockDto;
import com.keiko.shopservice.dto.model.shop.ShopDto;
import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.Shop;
import com.keiko.shopservice.service.resources.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
public class ShopToDtoConverter extends AbstractToDtoConverter<Shop, ShopDto> {

    @Autowired
    private ProductService productService;

    @Autowired
    private Function<ProductStock, ProductStockDto> toDtoConverter;

    public ShopToDtoConverter () {
        super (Shop.class, ShopDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (Shop.class, ShopDto.class)
                .setPostConverter (converter);
    }

    @Override
    public void mapSpecificFields (Shop shop, ShopDto dto) {
        List<ProductStockDto> dtos = shop.getProductStock ()
                .stream ()
                .map (toDtoConverter::apply)
                .collect (toList ());
        dto.setProductStock (dtos);
    }
}
