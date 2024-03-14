package com.keiko.orderservice.util;

import com.keiko.commonservice.entity.resource.Address;
import com.keiko.commonservice.entity.resource.user.User;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.request.OrderEntryRequest;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    private static final Long ORDER_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long SHOP_ID = 1L;
    private static final Long ORDER_ENTRY_ID = 1L;

    private static final String ADDRESS_HOUSE = "house";
    private static final String ADDRESS_STREET = "street";
    private static final String ADDRESS_CITY = "city";
    private static final String ADDRESS_COUNTRY = "country";
    private static final String ADDRESS_POSTCODE = "postcode";

    private static final String GEOCODE_LAT = "53.652773771";
    private static final String GEOCODE_LNG = "23.830198953";

    private static final String PRODUCT_EAN = "ean";
    private static final Long PRODUCT_QUANTITY = 2L;

    public static Order testOrder () {
        Order order = new Order ();
        order.setId (ORDER_ID);
        order.setUserId (USER_ID);
        order.setShopId (SHOP_ID);
        order.setEntries (testOrderEntries ());
        return order;
    }

    private static List<OrderEntry> testOrderEntries () {
        List<OrderEntry> entries = new ArrayList<> ();
        OrderEntry entry = new OrderEntry (PRODUCT_EAN, 2L);
        entry.setId (ORDER_ENTRY_ID);
        entries.add (entry);
        return entries;
    }

    public static User testUser () {
        User user = new User ();
        user.setId (USER_ID);
        user.setUserAddress (testAddress ());
        return user;
    }

    public static DeliveryAddress testDeliveryAddress () {
        DeliveryAddress deliveryAddress = new DeliveryAddress ();
        deliveryAddress.setHouse (ADDRESS_HOUSE);
        deliveryAddress.setStreet (ADDRESS_STREET);
        deliveryAddress.setCity (ADDRESS_CITY);
        deliveryAddress.setCountry (ADDRESS_COUNTRY);
        deliveryAddress.setPostcode (ADDRESS_POSTCODE);
        return deliveryAddress;
    }

    public static Address testAddress () {
        Address address = new Address ();
        address.setHouse (ADDRESS_HOUSE);
        address.setStreet (ADDRESS_STREET);
        address.setCity (ADDRESS_CITY);
        address.setCountry (ADDRESS_COUNTRY);
        address.setPostcode (ADDRESS_POSTCODE);
        return address;
    }

    public static ReverseGeocodeRequest testReverseGeocodeRequest () {
        ReverseGeocodeRequest reverseGeocodeRequest = new ReverseGeocodeRequest ();
        reverseGeocodeRequest.setLat (GEOCODE_LAT);
        reverseGeocodeRequest.setLng (GEOCODE_LNG);
        return reverseGeocodeRequest;
    }

    public static OrderEntryRequest testOrderEntryRequest () {
        OrderEntryRequest orderEntryRequest = new OrderEntryRequest ();
        orderEntryRequest.setOrderId (ORDER_ID);
        orderEntryRequest.setProductEan (PRODUCT_EAN);
        orderEntryRequest.setQuantity (PRODUCT_QUANTITY);
        return orderEntryRequest;
    }
}
