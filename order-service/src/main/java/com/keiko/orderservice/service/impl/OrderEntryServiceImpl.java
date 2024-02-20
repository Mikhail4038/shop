package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.exception.model.OrderProcessException;
import com.keiko.orderservice.request.BookingOrderEntryRequest;
import com.keiko.orderservice.request.ModificationOrderRequest;
import com.keiko.orderservice.service.AbstractCrudService;
import com.keiko.orderservice.service.OrderEntryService;
import com.keiko.orderservice.service.resources.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class OrderEntryServiceImpl implements OrderEntryService {

    @Autowired
    private AbstractCrudService<Order> orderService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void saveOrderEntry (ModificationOrderRequest saveOrderEntryRequest) {
        String ean = saveOrderEntryRequest.getProductEan ();
        Long qty = saveOrderEntryRequest.getQuantity ();
        Long orderId = saveOrderEntryRequest.getOrderId ();

        Order order = orderService.fetchBy (orderId);
        checkOrderStatus (order);
        Long shopId = order.getShopId ();

        qty = beforeSaveOrderEntry (ean, shopId, qty);
        OrderEntry orderEntry = createOrGetOrderEntry (ean, qty, order);
        saveOrderEntry (orderEntry, order);
        afterSaveOrderEntry (buildBookedRequest (ean, qty, shopId));
    }

    @Override
    @Transactional
    public void removeOrderEntry (ModificationOrderRequest removeOrderEntryRequest) {
        String ean = removeOrderEntryRequest.getProductEan ();
        Long qty = removeOrderEntryRequest.getQuantity ();
        Long orderId = removeOrderEntryRequest.getOrderId ();

        Order order = orderService.fetchBy (orderId);
        checkOrderStatus (order);
        Long shopId = order.getShopId ();

        OrderEntry orderEntry = getOrderEntry (ean, order);
        removeOrChangeQtyOrderEntry (qty, orderEntry, order);
        afterRemoveOrderEntry (buildBookedRequest (ean, qty, shopId));
    }

    private OrderEntry createOrGetOrderEntry (String ean, Long qty, Order order) {
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

    private Long beforeSaveOrderEntry (String ean, Long shopId, Long qty) {
        Long availableForSell = shopService.countProductForSell (ean, shopId);
        return qty > availableForSell ? availableForSell : qty;
    }

    private void saveOrderEntry (OrderEntry orderEntry, Order order) {
        if (isNull (orderEntry.getId ())) {
            order.getEntries ().add (orderEntry);
        }
        eventPublisher.publishEvent (new RecalculateOrderEvent (order));
        orderService.save (order);
    }

    private void afterSaveOrderEntry (BookingOrderEntryRequest bookedRequest) {
        shopService.bookedStock (bookedRequest);
    }

    private OrderEntry getOrderEntry (String ean, Order order) {
        List<OrderEntry> entries = order.getEntries ();
        OrderEntry orderEntry = entries
                .stream ()
                .filter (entry -> entry.getProductEan ().equals (ean))
                .findFirst ().get ();
        return orderEntry;
    }

    private void removeOrChangeQtyOrderEntry (Long qty, OrderEntry orderEntry, Order order) {
        List<OrderEntry> entries = order.getEntries ();
        Long quantity = orderEntry.getQuantity ();
        quantity -= qty;

        if (quantity > 0) {
            orderEntry.setQuantity (quantity);
        } else {
            entries.remove (orderEntry);
        }
        eventPublisher.publishEvent (new RecalculateOrderEvent (order));
        orderService.save (order);
    }

    private void afterRemoveOrderEntry (BookingOrderEntryRequest cancelBookedRequest) {
        shopService.cancelBookedStock (cancelBookedRequest);
    }

    private BookingOrderEntryRequest buildBookedRequest (String ean, Long qty, Long shopId) {
        return BookingOrderEntryRequest.builder ()
                .ean (ean)
                .quantity (qty)
                .shopId (shopId)
                .build ();
    }

    private void checkOrderStatus (Order order) {
        OrderStatus orderStatus = order.getOrderStatus ();
        if (!orderStatus.equals (OrderStatus.CREATED)) {
            throw new OrderProcessException ("Please, check order status");
        }
    }
}
