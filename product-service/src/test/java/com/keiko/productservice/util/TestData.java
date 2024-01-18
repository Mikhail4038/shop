package com.keiko.productservice.util;

import com.keiko.productservice.dto.model.product.ProductData;
import com.keiko.productservice.dto.model.product.ProductDto;
import com.keiko.productservice.dto.model.review.ReviewData;
import com.keiko.productservice.dto.model.review.ReviewDto;
import com.keiko.productservice.entity.*;
import com.keiko.productservice.event.RecalculateProductRatingEvent;

import java.sql.Timestamp;
import java.time.LocalDate;

public class TestData {
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_EAN = "1234";
    private static final String PRODUCT_NAME = "jersey";

    private static final Long PRODUCER_ID = 1L;
    private static final String PRODUCER_NAME = "Conte";

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "user@gmail.com";
    private static final String USER_NAME = "user";

    private static final String REVIEW_MESSAGE = "Good product";
    private static final Integer REVIEW_ASSESSMENT = 5;

    private static final Double PRICE_COST = 40.0;

    private static final Integer COUNT_REVIEW = 3;
    private static final Float AVERAGE_ASSESSMENT = 7.5F;

    private static final String ADDRESS_STREET = "street";
    private static final String ADDRESS_HOUSE = "house";
    private static final String ADDRESS_CITY = "city";
    private static final String ADDRESS_COUNTRY = "country";
    private static final String ADDRESS_LOCALE = "locale";


    public static Product testProduct () {
        Product product = new Product ();
        product.setId (PRODUCT_ID);
        product.setEan (PRODUCT_EAN);
        product.setName (PRODUCT_NAME);
        return product;
    }

    public static ProductData testProductData () {
        ProductData data = new ProductData ();
        data.setId (PRODUCT_ID);
        data.setEan (PRODUCT_EAN);
        data.setName (PRODUCT_NAME);
        return data;
    }

    public static ProductDto testProductDto () {
        ProductDto dto = new ProductDto ();
        dto.setId (PRODUCT_ID);
        dto.setEan (PRODUCT_EAN);
        dto.setName (PRODUCT_NAME);
        return dto;
    }

    public static Producer testProducer () {
        Producer producer = new Producer ();
        producer.setId (PRODUCER_ID);
        producer.setName (PRODUCER_NAME);
        return producer;
    }

    public static Review testReview () {
        Review review = new Review ();
        review.setUserId (USER_ID);
        review.setMessage (REVIEW_MESSAGE);
        review.setAssessment (REVIEW_ASSESSMENT);
        return review;
    }

    public static ReviewDto testReviewDto () {
        ReviewDto dto = new ReviewDto ();
        dto.setMessage (REVIEW_MESSAGE);
        dto.setAssessment (REVIEW_ASSESSMENT);
        return dto;
    }

    public static ReviewData testReviewData () {
        ReviewData data = new ReviewData ();
        data.setMessage (REVIEW_MESSAGE);
        data.setAssessment (REVIEW_ASSESSMENT);
        return data;
    }

    public static Price testPrice () {
        Price price = new Price ();
        price.setValue (PRICE_COST);
        return price;
    }

    public static Rating testRating () {
        Rating rating = new Rating ();
        rating.setCountReviews (COUNT_REVIEW);
        rating.setValue (AVERAGE_ASSESSMENT);
        return rating;
    }

    public static Address testAddress () {
        Address address = new Address ();
        address.setStreet (ADDRESS_STREET);
        address.setHouse (ADDRESS_HOUSE);
        address.setCity (ADDRESS_CITY);
        address.setCountry (ADDRESS_COUNTRY);
        address.setLocale (ADDRESS_LOCALE);
        return address;
    }

    public static RecalculateProductRatingEvent recalculateProductRatingEvent () {
        Rating rating = testRating ();
        Product product = testProduct ();
        product.setRating (rating);
        Review review = testReview ();
        review.setProduct (product);
        RecalculateProductRatingEvent event = new RecalculateProductRatingEvent (review);
        return event;
    }

    public static User testUser () {
        User user = new User ();
        user.setId (USER_ID);
        user.setEmail (USER_EMAIL);
        user.setName (USER_NAME);
        return user;
    }
}
