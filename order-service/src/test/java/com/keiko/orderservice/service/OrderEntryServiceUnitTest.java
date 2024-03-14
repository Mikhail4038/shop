package com.keiko.orderservice.service;

import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.exception.model.OrderProcessException;
import com.keiko.orderservice.request.OrderEntryRequest;
import com.keiko.orderservice.service.impl.OrderEntryServiceImpl;
import com.keiko.orderservice.service.resources.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static com.keiko.orderservice.util.TestData.testOrder;
import static com.keiko.orderservice.util.TestData.testOrderEntryRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
public class OrderEntryServiceUnitTest {

    private static final Long POSITIVE_PRODUCT_STOCK = 5L;
    private static final Long NEGATIVE_PRODUCT_STOCK = 1L;

    @Mock
    private DefaultCrudService<Order> orderService;

    @Mock
    private ShopService shopService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @InjectMocks
    private OrderEntryServiceImpl orderEntryService;

    private OrderEntryRequest orderEntryRequest;
    private Order order;

    @BeforeEach
    void setUp () {
        orderEntryRequest = testOrderEntryRequest ();
        order = testOrder ();
    }

    @Test
    void should_fail_saveOrderEntry_wrongOrderStatus () {
        Long orderId = orderEntryRequest.getOrderId ();
        order.setOrderStatus (OrderStatus.PLACED);

        when (orderService.fetchBy (orderId)).thenReturn (order);

        assertThrows (OrderProcessException.class,
                () -> orderEntryService.saveOrderEntry (orderEntryRequest));

        verify (orderService, times (1)).fetchBy (anyLong ());
    }

    @Test
    void should_successfully_saveOrderEntry () {
        Long orderId = orderEntryRequest.getOrderId ();
        String ean = orderEntryRequest.getProductEan ();
        Long shopId = order.getShopId ();
        order.setOrderStatus (OrderStatus.CREATED);

        when (orderService.fetchBy (orderId)).thenReturn (order);
        when (shopService.countProductForSell (ean, shopId)).thenReturn (POSITIVE_PRODUCT_STOCK);

        orderEntryService.saveOrderEntry (orderEntryRequest);

        verify (shopService, times (1)).countProductForSell (anyString (), anyLong ());
        verify (orderService, times (1)).fetchBy (anyLong ());
        verify (eventPublisher, times (1)).publishEvent (any (RecalculateOrderEvent.class));
        verify (orderService, times (1)).save (any ());
        verify (shopService, times (1)).bookStock (any ());

        verify (orderService).save (orderArgumentCaptor.capture ());
        Order orderCaptorValue = orderArgumentCaptor.getValue ();
        assertEquals (4L, orderCaptorValue.getEntries ().get (0).getQuantity ());
    }

    @Test
    void should_successfully_saveOrderEntry_onlyAvailableInStock () {
        Long orderId = orderEntryRequest.getOrderId ();
        String ean = orderEntryRequest.getProductEan ();
        Long shopId = order.getShopId ();
        order.setOrderStatus (OrderStatus.CREATED);

        when (orderService.fetchBy (orderId)).thenReturn (order);
        when (shopService.countProductForSell (ean, shopId)).thenReturn (NEGATIVE_PRODUCT_STOCK);

        orderEntryService.saveOrderEntry (orderEntryRequest);

        verify (shopService, times (1)).countProductForSell (anyString (), anyLong ());
        verify (orderService, times (1)).fetchBy (anyLong ());
        verify (eventPublisher, times (1)).publishEvent (any (RecalculateOrderEvent.class));
        verify (orderService, times (1)).save (any ());
        verify (shopService, times (1)).bookStock (any ());

        verify (orderService).save (orderArgumentCaptor.capture ());
        Order orderCaptorValue = orderArgumentCaptor.getValue ();
        assertEquals (3L, orderCaptorValue.getEntries ().get (0).getQuantity ());
    }

    @Test
    void should_fail_removeOrderEntry_wrongOrderStatus () {
        Long orderId = orderEntryRequest.getOrderId ();
        order.setOrderStatus (OrderStatus.PLACED);

        when (orderService.fetchBy (orderId)).thenReturn (order);

        assertThrows (OrderProcessException.class, () -> orderEntryService.removeOrderEntry (orderEntryRequest));

        verify (orderService, times (1)).fetchBy (anyLong ());
    }

    @Test
    void should_successfully_removeOrderEntry () {
        Long orderId = orderEntryRequest.getOrderId ();
        order.setOrderStatus (OrderStatus.CREATED);

        when (orderService.fetchBy (orderId)).thenReturn (order);

        orderEntryService.removeOrderEntry (orderEntryRequest);

        verify (orderService, times (1)).fetchBy (anyLong ());
        verify (eventPublisher, times (1)).publishEvent (any (RecalculateOrderEvent.class));
        verify (orderService, times (1)).save (any (Order.class));
        verify (shopService, times (1)).cancelBookStock (any ());

        verify (orderService).save (orderArgumentCaptor.capture ());
        Order orderCaptorValue = orderArgumentCaptor.getValue ();
        assertTrue (orderCaptorValue.getEntries ().isEmpty ());
    }

    @Test
    void should_successfully_changeQtyOrderEntry () {
        orderEntryRequest.setQuantity (1L);

        Long orderId = orderEntryRequest.getOrderId ();
        order.setOrderStatus (OrderStatus.CREATED);

        when (orderService.fetchBy (orderId)).thenReturn (order);

        orderEntryService.removeOrderEntry (orderEntryRequest);

        verify (orderService, times (1)).fetchBy (anyLong ());
        verify (eventPublisher, times (1)).publishEvent (any (RecalculateOrderEvent.class));
        verify (orderService, times (1)).save (any (Order.class));
        verify (shopService, times (1)).cancelBookStock (any ());

        verify (orderService).save (orderArgumentCaptor.capture ());
        Order orderCaptorValue = orderArgumentCaptor.getValue ();
        assertFalse (orderCaptorValue.getEntries ().isEmpty ());

    }
}
