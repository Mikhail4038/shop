package com.keiko.commonservice.constants;

/**
 * This class includes all the API end points
 */
public class WebResourceKeyConstants {
    //common
    public static final String SAVE = "/save";
    public static final String FETCH_BY = "/fetchBy";
    public static final String FETCH_ALL = "/fetchAll";
    public static final String DELETE = "/delete";

    // services base
    public static final String USER_BASE = "/users";
    public static final String ROLE_BASE = "/roles";
    public static final String SHOP_BASE = "/shops";
    public static final String PRODUCT_BASE = "/products";
    public static final String PRODUCT_STOCK_BASE = "/stocks";
    public static final String ORDER_BASE = "/orders";
    public static final String PAYPAL_BASE = "/paypal";
    public static final String ADDRESS_BASE = "/address";

    // user
    public static final String IS_EXISTS_USER = "/isExists";
    public static final String FIND_USER_BY_EMAIL = "/findByEmail";
    public static final String FIND_NOT_ENABLED_USERS = "/findNotEnabled";
    public static final String DELETE_USER_BY_EMAIL = "/deleteByEmail";

    //product
    public static final String FIND_BY_EAN = "/findByEan";
    public static final String IS_EXIST = "/isExist";

    //stock
    public static final String COUNT_PRODUCT_STOCK_FOR_SELL = "/stockForSell";
    public static final String BOOK_STOCK = "/book";
    public static final String CANCEL_BOOK_STOCK = "/cancelBook";
    public static final String SELL_STOCK = "/sell";

    //address
    public static final String REVERSE_GEOCODE = "/reverseGeocode";
    public static final String CALCULATE_ROUTE = "/calculateRoute";

    //payment
    public static final String CREATE_PAYMENT = "/create";
    public static final String COMPLETE_PAYMENT = "/complete";

}
