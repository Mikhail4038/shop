package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductPromoService {
    List<Product> findPromoProducts (Boolean sortByAscend);
}
