package com.keiko.productservice.constants;

public class WebResourceKeyConstants {

    //product
    public static final String SEARCH = "/search";
    public static final String BY_EAN = "/ean";
    public static final String IS_EXIST = "/isExist";

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

    //producer
    public static final String PRODUCER_BASE = "/producer";

    //reviews
    public static final String REVIEW_BASE = "/review";
    public static final String USER_REVIEWS = "/userReviews";
    public static final String PRODUCT_REVIEWS = "/productReviews";

    //user
    public static final String FETCH_BY_ID = "/fetchBy";
}
