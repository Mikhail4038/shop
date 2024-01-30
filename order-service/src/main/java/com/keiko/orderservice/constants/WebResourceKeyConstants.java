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
    public static final String ADD_ENTRY_TO_ORDER = "/addEntry";

    // stock
    public static final String PRODUCT_STOCK_BASE = "/stock";
    public static final String COUNT_PRODUCT_STOCK_FOR_SELL = "/stockForSell";
    public static final String REDUCE_STOCK_LEVEL = "/reduceStock";

    //product
    public static final String PRODUCT_BASE = "/product";
    public static final String BY_EAN = "/ean";
}
