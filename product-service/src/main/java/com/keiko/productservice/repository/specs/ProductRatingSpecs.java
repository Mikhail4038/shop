package com.keiko.productservice.repository.specs;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Product_;
import com.keiko.productservice.entity.Rating;
import com.keiko.productservice.entity.Rating_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.nonNull;

public class ProductRatingSpecs {
    public static Specification<Product> hasRatingOfLessThan (Float averageAssessment,
                                                              Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Rating> productRating = root.join (Product_.RATING);
            if (nonNull (sortByAscend)) {
                setUpSortByRatingValue (query, builder, sortByAscend, productRating);
            }
            return builder.lessThan (productRating.get (Rating_.AVERAGE_ASSESSMENT), averageAssessment);
        };
    }

    public static Specification<Product> hasRatingOfMoreThan (Float averageAssessment,
                                                              Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Rating> productRating = root.join (Product_.RATING);
            if (nonNull (sortByAscend)) {
                setUpSortByRatingValue (query, builder, sortByAscend, productRating);
            }
            return builder.greaterThan (productRating.get (Rating_.AVERAGE_ASSESSMENT), averageAssessment);
        };
    }

    public static Specification<Product> hasRatingBetween (Float minAverageAssessment,
                                                           Float maxAverageAssessment,
                                                           Boolean sortByAscend) {
        return (root, query, builder) -> {
            Join<Product, Rating> productRating = root.join (Product_.RATING);
            if (nonNull (sortByAscend)) {
                setUpSortByRatingValue (query, builder, sortByAscend, productRating);
            }
            return builder.between (productRating.get (Rating_.AVERAGE_ASSESSMENT), minAverageAssessment, maxAverageAssessment);
        };
    }

    private static void setUpSortByRatingValue (CriteriaQuery<?> query, CriteriaBuilder builder,
                                                boolean sortByAscend, Join<Product, Rating> productRating) {
        if (sortByAscend) {
            query.orderBy (builder.asc (productRating.get (Rating_.AVERAGE_ASSESSMENT)));
        }
        if (!sortByAscend) {
            query.orderBy (builder.desc (productRating.get (Rating_.AVERAGE_ASSESSMENT)));
        }
    }
}
