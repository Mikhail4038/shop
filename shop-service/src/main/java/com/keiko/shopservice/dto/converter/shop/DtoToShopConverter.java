package com.keiko.shopservice.dto.converter.shop;

import com.keiko.shopservice.dto.converter.AbstractToEntityConverter;
import com.keiko.shopservice.dto.model.shop.ShopDto;
import com.keiko.shopservice.entity.Shop;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DtoToShopConverter extends AbstractToEntityConverter<ShopDto, Shop> {

    public DtoToShopConverter () {
        super (ShopDto.class, Shop.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (ShopDto.class, Shop.class)
                .addMappings (mapper -> mapper.skip (Shop::setProductStock));
    }

    @Override
    public void mapSpecificFields (ShopDto dto, Shop entity) {

    }
}
