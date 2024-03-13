package com.keiko.orderservice.util;

import com.keiko.commonservice.entity.resource.Address;
import com.keiko.commonservice.entity.resource.user.User;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;

public class TestData {

    private static final Long ORDER_ID = 1L;
    private static final Long USER_ID = 1L;

    private static final String ADDRESS_HOUSE = "house";
    private static final String ADDRESS_STREET = "street";
    private static final String ADDRESS_CITY = "city";
    private static final String ADDRESS_COUNTRY = "country";
    private static final String ADDRESS_POSTCODE = "postcode";

    private static final String GEOCODE_LAT = "53.652773771";
    private static final String GEOCODE_LNG = "23.830198953";

    public static Order testOrder () {
        Order order = new Order ();
        order.setId (ORDER_ID);
        order.setUserId (USER_ID);
        return order;
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
}
