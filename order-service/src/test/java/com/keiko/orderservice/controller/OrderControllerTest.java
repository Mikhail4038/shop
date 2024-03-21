package com.keiko.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.orderservice.dto.model.OrderDto;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.request.OrderEntryRequest;
import com.keiko.orderservice.service.DeliveryAddressService;
import com.keiko.orderservice.service.OrderEntryService;
import com.keiko.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.function.Function;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.ORDER_BASE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;
import static com.keiko.orderservice.util.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest (OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private DeliveryAddressService deliveryAddressService;

    @MockBean
    private OrderEntryService orderEntryService;

    @MockBean
    private DefaultCrudService<Order> crudService;

    @MockBean
    private Function<Order, OrderDto> toDtoConverter;

    @MockBean
    private Function<OrderDto, Order> toEntityConverter;

    private static OrderEntryRequest orderEntryRequest;
    private static DeliveryAddress deliveryAddress;
    private static ReverseGeocodeRequest reverseGeocodeRequest;
    private static Order order;
    private static OrderDto orderDto;

    @BeforeAll
    static void setUp () {
        orderEntryRequest = testOrderEntryRequest ();
        deliveryAddress = testDeliveryAddress ();
        reverseGeocodeRequest = testReverseGeocodeRequest ();
        order = testOrder ();
        orderDto = testOrderDto ();
    }

    @Test
    void createOrder_should_successfully () throws Exception {
        mockMvc.perform (get (ORDER_BASE + CREATE_ORDER + "/" + USER_ID + "/" + SHOP_ID)
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isCreated ());

        verify (orderService, times (1)).createOrder (anyLong (), anyLong ());
    }

    @Test
    void cancelOrder_should_successfully () throws Exception {
        mockMvc.perform (get (ORDER_BASE + CANCEL_ORDER)
                .queryParam ("orderId", ORDER_ID.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (orderService, times (1)).cancelOrder (anyLong ());
    }

    @Test
    void saveOrderEntry_should_successfully () throws Exception {
        mockMvc.perform (post (ORDER_BASE + SAVE_ORDER_ENTRY)
                .content (objectMapper.writeValueAsString (orderEntryRequest))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (orderEntryService, times (1)).saveOrderEntry (any (OrderEntryRequest.class));
    }

    @Test
    void removeOrderEntry_should_successfully () throws Exception {
        mockMvc.perform (post (ORDER_BASE + REMOVE_ORDER_ENTRY)
                .content (objectMapper.writeValueAsString (orderEntryRequest))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (orderEntryService, times (1)).removeOrderEntry (any (OrderEntryRequest.class));
    }

    @Test
    void saveDeliveryAddress_should_successfully () throws Exception {
        mockMvc.perform (post (ORDER_BASE + SAVE_DELIVERY_ADDRESS + "/" + ORDER_ID)
                .content (objectMapper.writeValueAsString (deliveryAddress))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (deliveryAddressService, times (1)).saveDeliveryAddress (any (DeliveryAddress.class), anyLong ());
    }

    @Test
    void userAddressToDeliveryAddress_should_successfully () throws Exception {
        mockMvc.perform (get (ORDER_BASE + SAVE_DELIVERY_ADDRESS + "/" + ORDER_ID)
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (deliveryAddressService, times (1)).saveDeliveryAddress (anyLong ());
    }

    @Test
    void pointDeliveryAddressAtMap_should_successfully () throws Exception {
        mockMvc.perform (post (ORDER_BASE + POINT_DELIVERY_ADDRESS + "/" + ORDER_ID)
                .content (objectMapper.writeValueAsString (reverseGeocodeRequest))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (deliveryAddressService, times (1)).saveDeliveryAddress (any (ReverseGeocodeRequest.class), anyLong ());
    }

    @Test
    void placeOrder_should_successfully () throws Exception {
        mockMvc.perform (get (ORDER_BASE + PLACE_ORDER)
                .queryParam ("orderId", ORDER_ID.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (orderService, times (1)).placeOrder (anyLong ());
    }

    @Test
    void fetchByStatus_should_successfully () throws Exception {
        OrderStatus orderStatus = OrderStatus.CREATED;
        order.setOrderStatus (orderStatus);

        when (orderService.fetchByStatus (orderStatus)).thenReturn (Arrays.asList (order));
        when (toDtoConverter.apply (order)).thenReturn (orderDto);

        mockMvc.perform (get (ORDER_BASE + FIND_BY_STATUS)
                .queryParam ("status", orderStatus.toString ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0].shop.id", is (orderDto.getShop ().getId ()), Long.class))
                .andExpect (jsonPath ("$[0].user.id", is (orderDto.getUser ().getId ()), Long.class))
                .andExpect (status ().isOk ());

        verify (orderService, times (1)).fetchByStatus (any (OrderStatus.class));
        verify (toDtoConverter, times (1)).apply (any (Order.class));
    }
}
