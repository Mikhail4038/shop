package com.keiko.productservice.dto.converter.product;

import com.keiko.productservice.dto.converter.AbstractToEntityConverter;
import com.keiko.productservice.dto.model.producer.ProducerData;
import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.entity.Producer;
import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Review;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class DtoToProductConverter extends AbstractToEntityConverter<ProductDto, Product> {

    public DtoToProductConverter () {
        super (ProductDto.class, Product.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (ProductDto.class, Product.class)
                .addMappings (mapper -> mapper.skip (Product::setRating))
                .addMappings (mapper -> mapper.skip (Product::setReviews));
    }

    @Override
    protected void mapSpecificFields (ProductDto dto, Product entity) {
        ProducerData producerData = dto.getProducer ();
        if (nonNull (producerData)) {
            ModelMapper producerMapper = new ModelMapper ();
            Producer producer = producerMapper.map (producerData, Producer.class);
            entity.setProducer (producer);
        }

        List<ReviewData> reviewsData = dto.getReviews ();
        if (nonNull (reviewsData) && !reviewsData.isEmpty ()) {
            ModelMapper reviewMapper = new ModelMapper ();
            List<Review> reviews = reviewsData.stream ()
                    .map (data -> reviewMapper.map (data, Review.class))
                    .collect (Collectors.toList ());
            entity.setReviews (reviews);
        }
    }
}
