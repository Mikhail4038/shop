package com.keiko.shopservice.dto.model.shop;

import com.keiko.shopservice.dto.model.BaseDto;
import com.keiko.shopservice.dto.model.productStock.ProductStockDto;
import com.keiko.shopservice.entity.ShopAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopDto extends BaseDto {
    private List<ProductStockDto> productStock;
    private ShopAddress shopAddress;
}
