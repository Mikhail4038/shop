package com.keiko.stockservice.repository.specs;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.entity.StopList;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProductStockSpec {
    public static Specification<ProductStock> isExpired () {
        return (root, query, builder) -> {
            LocalDate now = LocalDate.now ();
            return builder.lessThan (root.get ("expirationDate"), now);
        };
    }

    public static Specification<ProductStock> inStopList (StopList stopList) {
        return (root, query, builder) -> {
            return builder.equal (root.get ("stopList"), stopList);
        };
    }
}
