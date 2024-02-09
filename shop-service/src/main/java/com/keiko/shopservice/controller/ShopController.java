package com.keiko.shopservice.controller;

import com.keiko.shopservice.dto.model.shop.ShopDto;
import com.keiko.shopservice.entity.Shop;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.shopservice.constants.WebResourceKeyConstants.SHOP_BASE;

@RestController
@RequestMapping (value = SHOP_BASE)
public class ShopController extends AbstractCrudController<Shop, ShopDto> {
}
