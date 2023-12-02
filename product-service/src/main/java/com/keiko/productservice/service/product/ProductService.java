package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> searchProduct (Long producerId, boolean isPromotional,
                                 Double minPrice, Double maxPrice,
                                 Float minRating, Float maxRating);
}
