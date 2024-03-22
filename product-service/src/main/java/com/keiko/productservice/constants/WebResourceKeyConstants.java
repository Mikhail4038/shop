package com.keiko.productservice.constants;

public class WebResourceKeyConstants {
    public static final String RANGE = "/isBetween";

    //product
    public static final String ADVANCED_SEARCH = "/advancedSearch";

    // product price
    public static final String PRICE_BASE = "/price";
    public static final String PRICE_LESS_THAN = "/isLessThan";
    public static final String PRICE_MORE_THAN = "/isMoreThan";

    // product rating
    public static final String RATING_BASE = "/rating";
    public static final String RATING_LESS_THAN = "/isLessThan";
    public static final String RATING_MORE_THAN = "/isMoreThan";

    // product producer
    public static final String PRODUCER_BASE = "/producers";

    // product promo
    public static final String PROMO_BASE = "/promo";
    public static final String FIND_PROMO = "/isPromo";

    // product review
    public static final String REVIEW_BASE = "/reviews";
    public static final String USER_REVIEWS = "/findByUser";
    public static final String PRODUCT_REVIEWS = "/findByProduct";

    //user
    public static final String FETCH_BY = "/fetchBy";
}
