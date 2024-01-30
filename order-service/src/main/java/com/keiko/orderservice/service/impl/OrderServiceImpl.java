package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.request.AddEntryToOrderRequest;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends AbstractCrudServiceImpl<Order>
        implements OrderService {

    @Autowired
    private StockService stockService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void addOrderEntry (AddEntryToOrderRequest request) {
        Order order = super.fetchBy (request.getOrderId ());

        OrderEntry orderEntry = OrderEntry.builder ()
                .productEan (request.getProductEan ())
                .quantity (request.getQuantity ())
                .build ();

        beforeAddEntry (orderEntry);
        addEntry (orderEntry, order);
        afterAddEntry (orderEntry);
    }

    @Override
    public void removeOrderEntry (OrderEntry orderEntry, Order order) {
        order.getEntries ().remove (orderEntry);
        super.save (order);
    }

    @Override
    public void placeOrder (Order order) {

    }

    private void beforeAddEntry (OrderEntry orderEntry) {
        String ean = orderEntry.getProductEan ();
        Long qty = orderEntry.getQuantity ();

        Long availableForSell = stockService.countProductForSell (ean);

        if (qty > availableForSell) {
            orderEntry.setQuantity (availableForSell);
        }
    }

    private void addEntry (OrderEntry orderEntry, Order order) {
        String ean = orderEntry.getProductEan ();
        Long qty = orderEntry.getQuantity ();

        List<OrderEntry> entries = order.getEntries ();
        Optional<OrderEntry> entry = entries.stream ()
                .filter (e -> e.getProductEan ().equals (ean))
                .findFirst ();

        if (entry.isPresent ()) {
            OrderEntry savedEntry = entry.get ();
            Long quantity = savedEntry.getQuantity ();
            quantity += qty;
            savedEntry.setQuantity (quantity);
        } else {
            entries.add (orderEntry);
        }
        eventPublisher.publishEvent (new RecalculateOrderEvent (order));
        super.save (order);

        stockService.reduceStock (ean, qty);
    }

    private void afterAddEntry (OrderEntry orderEntry) {
        // payment
        // send notification
    }
}
