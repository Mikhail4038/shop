package com.keiko.orderservice.util;

import com.keiko.commonservice.entity.resource.Address;
import com.keiko.commonservice.entity.resource.Shop;
import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.commonservice.entity.resource.product.Price;
import com.keiko.commonservice.entity.resource.product.Product;
import com.keiko.commonservice.entity.resource.user.User;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.orderservice.dto.model.OrderDto;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.resources.PaymentOrder;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.request.OrderEntryRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static final Long ORDER_ID = 1L;
    public static final Long USER_ID = 1L;
    public static final Long SHOP_ID = 1L;
    private static final Long ORDER_ENTRY_ID = 1L;
    private static final BigDecimal TOTAL_AMOUNT = BigDecimal.valueOf (100);

    private static final String ADDRESS_HOUSE = "house";
    private static final String ADDRESS_STREET = "street";
    private static final String ADDRESS_CITY = "city";
    private static final String ADDRESS_COUNTRY = "country";
    private static final String ADDRESS_POSTCODE = "postcode";

    private static final String GEOCODE_LAT = "53.652773771";
    private static final String GEOCODE_LNG = "23.830198953";

    private static final String PRODUCT_EAN = "ean";
    private static final Long PRODUCT_QUANTITY = 2L;

    private static final String APPROVE_URL = "http://approve.your.order";
    public static final String PAY_ID = "1";
    private static final String PAYMENT_ORDER_STATUS = "ok";

    private static final String COMPLETE_ORDER_MESSAGE = "complete";
    private static final String COMPLETE_ORDER_STATUS = "ok";

    private static final BigDecimal PRICE_VALUE = BigDecimal.valueOf (10);

    public static Order testOrder () {
        Order order = new Order ();
        order.setId (ORDER_ID);
        order.setUserId (USER_ID);
        order.setShopId (SHOP_ID);
        order.setEntries (testOrderEntries ());
        order.setTotalAmount (TOTAL_AMOUNT);
        return order;
    }

    public static OrderDto testOrderDto () {
        OrderDto dto = new OrderDto ();
        dto.setShop (testShop ());
        dto.setUser (testUser ());
        return dto;
    }

    public static Shop testShop () {
        Shop shop = new Shop ();
        shop.setId (SHOP_ID);
        shop.setShopAddress (testAddress ());
        return shop;
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

    public static PaymentOrder testPaymentOrder () {
        PaymentOrder paymentOrder = new PaymentOrder ();
        paymentOrder.setApproveUrl (APPROVE_URL);
        paymentOrder.setPayId (PAY_ID);
        paymentOrder.setStatus (PAYMENT_ORDER_STATUS);
        return paymentOrder;
    }

    public static CompletedOrder testCompletedOrder () {
        CompletedOrder completedOrder = new CompletedOrder ();
        completedOrder.setMessage (COMPLETE_ORDER_MESSAGE);
        completedOrder.setStatus (COMPLETE_ORDER_STATUS);
        return completedOrder;
    }

    public static RecalculateOrderEvent testRecalculateOrderEvent () {
        RecalculateOrderEvent recalculateOrderEvent = new RecalculateOrderEvent (testOrder ());
        return recalculateOrderEvent;
    }

    public static Product testProduct () {
        Product product = new Product ();
        product.setPrice (testPrice ());
        return product;
    }

    private static Price testPrice () {
        Price price = new Price ();
        price.setValue (PRICE_VALUE);
        return price;
    }
}
