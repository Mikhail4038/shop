package com.keiko.productservice.constants;

public class WebResourceKeyConstants {

    //product
    public static final String ADVANCED_SEARCH = "/advancedSearch";

    //product.price
    public static final String PRICE_BASE = "/price";
    public static final String PRICE_LESS_THAN = "/isLessThan";
    public static final String PRICE_MORE_THAN = "/isMoreThan";
    public static final String PRICE_RANGE = "/priceRange";

    //product.rating
    public static final String RATING_BASE = "/rating";
    public static final String RATING_LESS_THAN = "/isLessThan";
    public static final String RATING_MORE_THAN = "/isMoreThan";
    public static final String RATING_RANGE = "/ratingRange";

    //product.producer
    public static final String PRODUCT_PRODUCER_BASE = "/producer";
    public static final String FIND_BY_PRODUCER = "/findByProducer";
    public static final String FIND_PROMO_BY_PRODUCER = "/findPromoByProducer";

    //product promo
    public static final String PROMO_BASE = "/promo";
    public static final String FIND_PROMO = "/findPromo";

    //producer
    public static final String PRODUCER_BASE = "/producer";

    //reviews
    public static final String REVIEW_BASE = "/review";
    public static final String USER_REVIEWS = "/findByUser";
    public static final String PRODUCT_REVIEWS = "/findByProduct";

    //user
    public static final String FETCH_BY = "/fetchBy";
}
