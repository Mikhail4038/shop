package com.keiko.productservice.service.product.impl;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.repository.ProductRepository;
import com.keiko.productservice.service.impl.CrudServiceImpl;
import com.keiko.productservice.service.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.keiko.productservice.repository.specs.ProductExpirationSpecs.isExpirationDateSoon;
import static com.keiko.productservice.repository.specs.ProductExpirationSpecs.isExpired;
import static com.keiko.productservice.repository.specs.ProductPriceSpecs.*;
import static com.keiko.productservice.repository.specs.ProductProducerSpecs.equalsProducer;
import static com.keiko.productservice.repository.specs.ProductPromoSpecs.isPromotionalPrice;
import static com.keiko.productservice.repository.specs.ProductRatingSpecs.*;
import static java.util.Objects.nonNull;

@Service
public class ProductServiceImpl extends CrudServiceImpl<Product>
        implements ProductService, ProductPriceService, ProductRatingService,
        ProductPromoService, ProductProducerService, ProductExpirationService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findPromoProducts (Boolean sortByAscend) {
        return productRepository.findAll (isPromotionalPrice (sortByAscend));
    }

    @Override
    public List<Product> findProductsPriceLessThan (Double price, Boolean sortByAscend) {
        return productRepository.findAll (hasPriceOfLessThan (price, sortByAscend));
    }

    @Override
    public List<Product> findProductsPriceMoreThan (Double price, Boolean sortByAscend) {
        return productRepository.findAll (hasPriceOfMoreThan (price, sortByAscend));
    }

    @Override
    public List<Product> findProductsPriceRange (Double minPrice, Double maxPrice, Boolean sortByAscend) {
        return productRepository.findAll (hasPriceBetween (minPrice, maxPrice, sortByAscend));
    }

    @Override
    public List<Product> findProductsRatingLessThan (Double averageAssessment, Boolean sortByAscend) {
        return productRepository.findAll (hasRatingOfLessThan (averageAssessment, sortByAscend));
    }

    @Override
    public List<Product> findProductsRatingMoreThan (Double averageAssessment, Boolean sortByAscend) {
        return productRepository.findAll (hasRatingOfMoreThan (averageAssessment, sortByAscend));
    }

    @Override
    public List<Product> findProductsRatingRange (Float minAverageAssessment, Float maxAverageAssessment, Boolean sortByAscend) {
        return productRepository.findAll (hasRatingBetween (minAverageAssessment, maxAverageAssessment, sortByAscend));
    }

    @Override
    public List<Product> findProductsByProducer (Long producerId, Boolean sortByAscend) {
        return productRepository.findAll (equalsProducer (producerId, sortByAscend));
    }

    @Override
    public List<Product> findPromoProductByProducer (Long producerId, Boolean sortByAscend) {
        return productRepository.findAll (isPromotionalPrice (null).and (equalsProducer (producerId, sortByAscend)));
    }

    @Override
    public List<Product> findProductExpirationDateSoon (byte daysForPromo) {
        return productRepository.findAll (isExpirationDateSoon (daysForPromo));
    }

    @Override
    public void deleteExpiredProduct () {
        List<Product> products = productRepository.findAll (isExpired ());
        productRepository.deleteAll (products);
    }

    @Override
    public List<Product> searchProducts (Long producerId, boolean isPromotional,
                                         Double minPrice, Double maxPrice,
                                         Float minRating, Float maxRating) {
        Specification<Product> spec = Specification.where (null);

        if (nonNull (producerId)) {
            spec = spec.and (equalsProducer (producerId, null));
        }
        if (isPromotional) {
            spec = spec.and (isPromotionalPrice (null));
        }
        if (nonNull (minPrice) && nonNull (maxPrice)) {
            spec = spec.and (hasPriceBetween (minPrice, maxPrice, null));
        }
        if (nonNull (minRating) && nonNull (maxPrice)) {
            spec = spec.and (hasRatingBetween (minRating, maxRating, null));
        }
        return productRepository.findAll (spec);
    }
}
