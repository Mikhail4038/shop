package com.keiko.orderservice.service;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.PaymentOrder;
import com.keiko.orderservice.exception.model.OrderProcessException;
import com.keiko.orderservice.service.impl.PaypalServiceImpl;
import com.keiko.orderservice.service.resources.PaypalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.keiko.orderservice.util.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class PaymentServiceUnitTest {

    @Mock
    private PaypalService paypalService;

    @Mock
    private OrderService orderService;

    @Mock
    private DefaultCrudService<Order> orderCrudService;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @InjectMocks
    private PaypalServiceImpl paypalServiceImpl;

    private Order order;
    private PaymentOrder paymentOrder;
    private CompletedOrder completedOrder;

    @BeforeEach
    void setUp () {
        order = testOrder ();
        paymentOrder = testPaymentOrder ();
        completedOrder = testCompletedOrder ();
    }

    @Test
    void should_fail_createPayment_wrongOrderStatus () {
        order.setOrderStatus (OrderStatus.CREATED);
        Long orderId = order.getId ();

        when (orderCrudService.fetchBy (orderId)).thenReturn (order);
        assertThrows (OrderProcessException.class, () -> paypalServiceImpl.createPayment (orderId));

        verify (orderCrudService, times (1)).fetchBy (anyLong ());
    }

    @Test
    void should_successfully_createPayment () {
        order.setOrderStatus (OrderStatus.PLACED);
        Long orderId = order.getId ();
        BigDecimal totalAmount = order.getTotalAmount ();

        when (orderCrudService.fetchBy (orderId)).thenReturn (order);
        when (paypalService.createPayment (totalAmount)).thenReturn (paymentOrder);

        paypalServiceImpl.createPayment (orderId);

        verify (orderCrudService, times (1)).fetchBy (anyLong ());
        verify (paypalService, times (1)).createPayment (any ());
        verify (orderCrudService, times (1)).save (any (Order.class));

        verify (orderCrudService).save (orderArgumentCaptor.capture ());
        Order order = orderArgumentCaptor.getValue ();
        assertEquals (order.getPayId (), PAY_ID);
        assertEquals (order.getOrderStatus (), OrderStatus.APPROVED);
    }

    @Test
    void should_successfully_completePayment () {
        when (paypalService.completePayment (PAY_ID)).thenReturn (completedOrder);
        when (orderService.fetchByPayId (PAY_ID)).thenReturn (order);

        paypalServiceImpl.completePayment (PAY_ID);

        verify (paypalService, times (1)).completePayment (anyString ());
        verify (orderService, times (1)).fetchByPayId (anyString ());

        verify (orderCrudService).save (orderArgumentCaptor.capture ());
        Order order = orderArgumentCaptor.getValue ();
        assertEquals (order.getOrderStatus (), OrderStatus.PAID);
    }
}
