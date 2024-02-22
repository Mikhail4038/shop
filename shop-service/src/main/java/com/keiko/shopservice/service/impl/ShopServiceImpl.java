package com.keiko.shopservice.service.impl;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.Shop;
import com.keiko.shopservice.repository.ProductStockRepository;
import com.keiko.shopservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.keiko.shopservice.repository.specs.ProductStockSpec.byEan;
import static com.keiko.shopservice.repository.specs.ProductStockSpec.byShop;

@Service
public class ShopServiceImpl extends DefaultCrudServiceImpl<Shop>
        implements ShopService {

    @Autowired
    private ProductStockRepository productStockRepository;

    @Override
    public List<ProductStock> fetchStocksByEan (Long shopId, String ean) {
        return productStockRepository.findAll (byShop (shopId).and (byEan (ean)));
    }
}
