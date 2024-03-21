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
    public static final String FIND_BY_STATUS = "/findByStatus";

    // stock
    public static final String FETCH_SHOP_BY_ID = "/fetchBy";
}
