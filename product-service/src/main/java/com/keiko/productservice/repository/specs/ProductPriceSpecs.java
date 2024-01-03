package com.keiko.productservice.repository.specs;

import com.keiko.productservice.entity.Price;
import com.keiko.productservice.entity.Price_;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Product_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.nonNull;

public class ProductPriceSpecs {
    public static Specification<Product> hasPriceOfLessThan (Double price, Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Price> productPrice = root.join (Product_.PRICE);
            if (nonNull (sortByAscend)) {
                setUpSortByPriceValue (query, builder, productPrice, sortByAscend);
            }
            return builder.lessThan (productPrice.get (Price_.VALUE), price);
        };
    }

    public static Specification<Product> hasPriceOfMoreThan (Double price, Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Price> productPrice = root.join (Product_.PRICE);
            if (nonNull (sortByAscend)) {
                setUpSortByPriceValue (query, builder, productPrice, sortByAscend);
            }
            return builder.greaterThan (productPrice.get (Price_.VALUE), price);
        };
    }

    public static Specification<Product> hasPriceBetween (Double minPrice, Double maxPrice, Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Price> productPrice = root.join (Product_.PRICE);
            if (nonNull (sortByAscend)) {
                setUpSortByPriceValue (query, builder, productPrice, sortByAscend);
            }
            return builder.between (productPrice.get (Price_.VALUE), minPrice, maxPrice);
        };
    }

    public static void setUpSortByPriceValue (CriteriaQuery<?> query, CriteriaBuilder builder,
                                              Join<Product, Price> productPrice, boolean sortByAscend) {
        if (sortByAscend) {
            query.orderBy (builder.asc (productPrice.get (Price_.VALUE)));
        }
        if (!sortByAscend) {
            query.orderBy (builder.desc (productPrice.get (Price_.VALUE)));
        }
    }
}
