package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> advancedSearch (Long producerId, Boolean isPromotional,
                                  Double minPrice, Double maxPrice,
                                  Float minRating, Float maxRating);

    Product findByEan (String ean);

    Boolean isExist (String ean);
}
