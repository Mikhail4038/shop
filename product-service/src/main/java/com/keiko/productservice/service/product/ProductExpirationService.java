package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductExpirationService {
    List<Product> findProductExpirationDateSoon (byte daysForPromo);

    void deleteExpiredProduct ();
}
