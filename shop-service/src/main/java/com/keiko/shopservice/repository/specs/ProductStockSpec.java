package com.keiko.shopservice.repository.specs;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.ProductStock_;
import com.keiko.shopservice.entity.Shop;
import com.keiko.shopservice.entity.StopList;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProductStockSpec {

    public static Specification<ProductStock> hasShopStock (String ean, Shop shop) {
        return (root, query, builder) -> {
            Predicate byEan = builder.equal (root.get (ProductStock_.EAN), ean);
            Predicate byShop = builder.equal (root.get (ProductStock_.SHOP), shop);
            return builder.and (byEan, byShop);
        };
    }

    public static Specification<ProductStock> isExpired () {
        return (root, query, builder) -> {
            LocalDate now = LocalDate.now ();
            return builder.lessThan (root.get (ProductStock_.EXPIRATION_DATE), now);
        };
    }

    public static Specification<ProductStock> inStopList (StopList stopList) {
        return (root, query, builder) -> {
            return builder.equal (root.get (ProductStock_.STOP_LIST), stopList);
        };
    }

    public static Specification<ProductStock> byEan (String ean) {
        return (root, query, builder) -> {
            return builder.equal (root.get (ProductStock_.EAN), ean);
        };
    }

    public static Specification<ProductStock> hasBookedStock () {
        return (root, query, builder) -> {
            return builder.greaterThan (root.get (ProductStock_.BOOKED), 0);
        };
    }
}
