package com.keiko.productservice.repository.specs;

import com.keiko.productservice.entity.Price;
import com.keiko.productservice.entity.Price_;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Product_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import static com.keiko.productservice.repository.specs.ProductPriceSpecs.setUpSortByPriceValue;
import static java.util.Objects.nonNull;

public class ProductPromoSpecs {
    public static Specification<Product> isPromotionalPrice (Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Price> productPrice = root.join (Product_.PRICE);
            if (nonNull (sortByAscend)) {
                setUpSortByPriceValue (query, builder, productPrice, sortByAscend);
            }
            return builder.equal (productPrice.get (Price_.IS_PROMOTIONAL), true);
        };
    }
}
