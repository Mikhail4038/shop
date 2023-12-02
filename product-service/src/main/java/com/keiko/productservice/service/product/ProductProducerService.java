package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductProducerService {
    List<Product> findProductsByProducer (Long producerId, Boolean sortByAscend);

    List<Product> findPromoProductByProducer (Long producerId, Boolean sortByAscend);
}
