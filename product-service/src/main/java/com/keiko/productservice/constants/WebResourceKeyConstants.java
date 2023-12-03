package com.keiko.productservice.constants;

public class WebResourceKeyConstants {
    //common
    public static final String SAVE = "/save";
    public static final String FETCH_BY = "/fetchBy";
    public static final String FETCH_ALL = "/fetchAll";
    public static final String DELETE = "/delete";

    //product
    public static final String PRODUCT_BASE = "/product";
    public static final String BY_EAN = "/ean";

    //product.price
    public static final String PRICE_BASE = "/price";
    public static final String PRICE_LESS_THAN = "/less";
    public static final String PRICE_MORE_THAN = "/more";
    public static final String PRICE_RANGE = "/range";

    //product.rating
    public static final String RATING_BASE = "/rating";
    public static final String RATING_LESS_THAN = "/less";
    public static final String RATING_MORE_THAN = "/more";
    public static final String RATING_RANGE = "/range";

    //product.producer
    public static final String PRODUCT_PRODUCER_BASE = "/manufacturer";
    public static final String BY_PRODUCER = "/products";
    public static final String PROMO_BY_PRODUCER = "/promo";

    //product promo
    public static final String PROMO_BASE = "/promo";
    public static final String PROMO_PRODUCTS = "/products";

    public static final String SEARCH = "/search";

    //producer
    public static final String PRODUCER_BASE = "/producer";
    //address
    public static final String ADDRESS_BASE = "/address";

    //reviews
    public static final String REVIEW_BASE = "/review";
    public static final String USER_REVIEWS = "/userReviews";
    public static final String PRODUCT_REVIEWS = "/productReviews";

    //user
    public static final String USER_BASE = "/user";
    public static final String FETCH_BY_ID = "/fetchBy";
}
