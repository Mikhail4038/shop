package com.keiko.shopservice.service.impl;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.Shop;
import com.keiko.shopservice.repository.ProductStockRepository;
import com.keiko.shopservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.keiko.shopservice.repository.specs.ProductStockSpec.hasShopStock;

@Service
public class ShopServiceImpl extends AbstractCrudServiceImpl<Shop>
        implements ShopService {

    @Autowired
    private ProductStockRepository productStockRepository;

    @Override
    public List<ProductStock> fetchStocksByEan (String ean, Shop shop) {
        return productStockRepository.findAll (hasShopStock (ean, shop));
    }
}
