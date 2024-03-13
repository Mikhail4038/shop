package com.keiko.orderservice.constants;

/**
 * This class includes all the API end points
 */
public class WebResourceKeyConstants {
    // order
    public static final String CREATE_ORDER = "/create";
    public static final String CANCEL_ORDER = "/cancel";
    public static final String SAVE_ORDER_ENTRY = "/saveEntry";
    public static final String REMOVE_ORDER_ENTRY = "/removeEntry";
    public static final String SAVE_DELIVERY_ADDRESS = "/saveDeliveryAddress";
    public static final String POINT_DELIVERY_ADDRESS = "/pointDeliveryAddress";
    public static final String PLACE_ORDER = "/place";
    public static final String FETCH_BY_STATUS = "/byStatus";

    // stock
    public static final String COUNT_PRODUCT_STOCK_FOR_SELL = "/stockForSell";
    public static final String BOOKED_STOCK = "/booked";
    public static final String CANCEL_BOOKED_STOCK = "/cancelBooked";
    public static final String SELL_STOCK = "/sell";
    public static final String FETCH_SHOP_BY_ID = "/fetchBy";

    //product
    public static final String BY_EAN = "/ean";

    //address
    public static final String REVERSE_GEOCODE = "/reverseGeocode";
    public static final String CALCULATE_ROUTE = "/calculateRoute";

    //payment
    public static final String INIT_PAYMENT = "/init";
    public static final String COMPLETE_PAYMENT = "/complete";
}
