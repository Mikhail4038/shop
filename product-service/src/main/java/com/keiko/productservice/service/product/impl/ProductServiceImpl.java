package com.keiko.productservice.service.product.impl;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.exception.model.ProductNotFoundException;
import com.keiko.productservice.repository.ProductRepository;
import com.keiko.productservice.service.impl.DefaultCrudServiceImpl;
import com.keiko.productservice.service.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.keiko.productservice.repository.specs.ProductPriceSpecs.*;
import static com.keiko.productservice.repository.specs.ProductProducerSpecs.equalsProducer;
import static com.keiko.productservice.repository.specs.ProductPromoSpecs.isPromotionalPrice;
import static com.keiko.productservice.repository.specs.ProductRatingSpecs.*;
import static java.util.Objects.nonNull;

@Service
public class ProductServiceImpl extends DefaultCrudServiceImpl<Product>
        implements ProductService, ProductPriceService, ProductRatingService,
        ProductPromoService, ProductProducerService {

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
    public List<Product> findProductsPriceRange (Double minPrice,
                                                 Double maxPrice,
                                                 Boolean sortByAscend) {
        return productRepository.findAll (hasPriceBetween (minPrice, maxPrice, sortByAscend));
    }

    @Override
    public List<Product> findProductsRatingLessThan (Float rating, Boolean sortByAscend) {
        return productRepository.findAll (hasRatingOfLessThan (rating, sortByAscend));
    }

    @Override
    public List<Product> findProductsRatingMoreThan (Float rating, Boolean sortByAscend) {
        return productRepository.findAll (hasRatingOfMoreThan (rating, sortByAscend));
    }

    @Override
    public List<Product> findProductsRatingRange (Float minRating,
                                                  Float maxRating,
                                                  Boolean sortByAscend) {
        return productRepository.findAll (hasRatingBetween (minRating, maxRating, sortByAscend));
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
    public List<Product> advancedSearch (Long producerId, Boolean isPromotional,
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

    @Override
    public Product findByEan (String ean) {
        return productRepository.findByEan (ean).orElseThrow (() -> {
            String message = String.format ("Product with ean: %s not found", ean);
            return new ProductNotFoundException (message);
        });

    }

    @Override
    public Boolean isExist (String ean) {
        try {
            productRepository.findByEan (ean).orElseThrow ();
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
