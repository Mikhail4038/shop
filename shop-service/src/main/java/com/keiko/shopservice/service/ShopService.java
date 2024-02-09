package com.keiko.shopservice.service;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.Shop;

import java.util.List;

public interface ShopService {

    List<ProductStock> fetchStocksByEan (String ean, Shop shop);
}
