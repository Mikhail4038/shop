package com.keiko.stockservice.service;

import com.keiko.stockservice.entity.ProductStock;

import java.util.Collection;
import java.util.List;

public interface ProductStockService {
    void save (ProductStock productStock);

    void saveAll (Collection<ProductStock> productsStock);

    ProductStock fetchById (Long id);

    ProductStock fetchByEan (String ean);

    List<ProductStock> fetchAll ();

    void delete (Long id);

    boolean hasStock (String ean);
}
