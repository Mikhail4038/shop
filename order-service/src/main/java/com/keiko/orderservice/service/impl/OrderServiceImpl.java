package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.Address;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.OrderDetailsEmail;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.request.CalculatingRouteRequest;
import com.keiko.orderservice.request.UpgradeOrderRequest;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class OrderServiceImpl extends AbstractCrudServiceImpl<Order>
        implements OrderService {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void createOrder (Long userId) {
        Order order = new Order ();
        order.setUserId (userId);
        order.setOrderStatus (OrderStatus.CREATED);
        super.save (order);
    }

    @Override
    @Transactional
    public void saveOrderEntry (UpgradeOrderRequest request) {
        Long orderId = request.getOrderId ();
        Order order = super.fetchBy (orderId);
        Long qty = request.getQuantity ();
        String ean = request.getProductEan ();

        qty = beforeSaveEntry (ean, qty);
        OrderEntry orderEntry = createOrGetEntry (ean, qty, order);
        saveEntry (orderEntry, order);
        afterSaveEntry (orderEntry);
    }

    @Override
    @Transactional
    public void removeOrderEntry (UpgradeOrderRequest request) {
        Long orderId = request.getOrderId ();
        Order order = super.fetchBy (orderId);
        Long qty = request.getQuantity ();
        String ean = request.getProductEan ();

        List<OrderEntry> entries = order.getEntries ();
        OrderEntry orderEntry = entries
                .stream ()
                .filter (entry -> entry.getProductEan ().equals (ean))
                .findFirst ().get ();

        Long quantity = orderEntry.getQuantity ();
        quantity -= qty;

        if (quantity > 0) {
            orderEntry.setQuantity (quantity);
        } else {
            entries.remove (orderEntry);
        }

        eventPublisher.publishEvent (new RecalculateOrderEvent (order));
        super.save (order);

        stockService.cancelBookedStock (orderEntry);
    }

    @Override
    public void saveDeliveryAddress (Address deliveryAddress, Long orderId) {
        Order order = super.fetchBy (orderId);
        order.setDeliveryAddress (deliveryAddress);
        super.save (order);
    }

    @Override
    @Transactional
    public void placeOrder (Long orderId) {
        Order order = super.fetchBy (orderId);
        //1. entry (booked->sold):
        sellProductStocks (order);

        //2.calculate
        calculateFinalSumOrder (order);

        //3. send notification
        sendOrderDetailsEmail (order);
        //4.payment
        //5.order status
    }

    private OrderEntry createOrGetEntry (String ean, Long qty, Order order) {
        Optional<OrderEntry> entry = order.getEntries ()
                .stream ()
                .filter (e -> e.getProductEan ().equals (ean))
                .findFirst ();

        if (entry.isPresent ()) {
            OrderEntry orderEntry = entry.get ();
            Long quantity = orderEntry.getQuantity ();
            quantity += qty;
            orderEntry.setQuantity (quantity);
            return orderEntry;
        } else {
            return OrderEntry.builder ()
                    .productEan (ean)
                    .quantity (qty)
                    .build ();
        }
    }

    private Long beforeSaveEntry (String ean, Long qty) {
        Long availableForSell = stockService.countProductForSell (ean);
        return qty > availableForSell ? availableForSell : qty;
    }

    private void saveEntry (OrderEntry orderEntry, Order order) {
        if (isNull (orderEntry.getId ())) {
            order.getEntries ().add (orderEntry);
        }
        eventPublisher.publishEvent (new RecalculateOrderEvent (order));
        super.save (order);
    }

    private void afterSaveEntry (OrderEntry orderEntry) {
        stockService.bookedStock (orderEntry);
    }

    private void sellProductStocks (Order order) {
        List<OrderEntry> entries = order.getEntries ();
        stockService.sellProductStocks (entries);
    }

    private void calculateFinalSumOrder (Order order) {
        //1.calculate delivery cost
        //1.a calculate route
        CalculatingRouteRequest calculatingRouteRequest = new CalculatingRouteRequest ();
        calculatingRouteRequest.setFrom (null);
        calculatingRouteRequest.setTo (order.getDeliveryAddress ());
        addressService.calculateRoute (calculatingRouteRequest);
        //2.calculate total amount
    }

    private void sendOrderDetailsEmail (Order order) {
        String email = userService.fetchBy (order.getUserId ()).getEmail ();
        OrderDetailsEmail orderDetailsEmail = OrderDetailsEmail.builder ()
                .toAddress (email)
                .subject ("Thank you for placing the order")
                .message ("Order details: ")
                .order (order)
                .build ();
        notificationService.sendOrderDetails (orderDetailsEmail);
    }
}
