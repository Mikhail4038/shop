package com.keiko.shopservice.service;

import com.keiko.shopservice.entity.ProductStock;

import java.util.List;

public interface ShopService {

    List<ProductStock> fetchStocksByEan (Long shopId, String ean);
}
