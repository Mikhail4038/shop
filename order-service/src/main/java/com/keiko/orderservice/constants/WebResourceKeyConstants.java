package com.keiko.orderservice.constants;

/**
 * This class includes all the API end points
 */
public class WebResourceKeyConstants {
    //common
    public static final String SAVE = "/save";
    public static final String FETCH_BY = "/fetchBy";
    public static final String FETCH_ALL = "/fetchAll";
    public static final String DELETE = "/delete";

    // order
    public static final String ORDER_BASE = "/order";
    public static final String CREATE_ORDER = "/create";
    public static final String SAVE_ORDER_ENTRY = "/saveEntry";
    public static final String REMOVE_ORDER_ENTRY = "/removeEntry";
    public static final String SAVE_DELIVERY_ADDRESS = "/saveDeliveryAddress";
    public static final String PLACE_ORDER = "/place";

    // stock
    public static final String SHOP_BASE = "/shop";
    public static final String PRODUCT_STOCK_BASE = "/stock";
    public static final String COUNT_PRODUCT_STOCK_FOR_SELL = "/stockForSell";
    public static final String BOOKED_STOCK = "/booked";
    public static final String CANCEL_BOOKED_STOCK = "/cancelBooked";
    public static final String SELL_STOCK = "/sell";
    public static final String FETCH_SHOP_BY_ID = "/fetchBy";

    //product
    public static final String PRODUCT_BASE = "/product";
    public static final String BY_EAN = "/ean";

    //notification
    public static final String EMAIL_NOTIFICATION_BASE = "/email";
    public static final String ORDER_DETAILS = "/orderDetails";

    //user
    public static final String USER_BASE = "/user";

    //address
    public static final String ADDRESS_BASE = "/address";
    public static final String CALCULATE_ROUTE = "/calculateRoute";
}
