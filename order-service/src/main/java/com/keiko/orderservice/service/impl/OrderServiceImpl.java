package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.request.AddEntryToOrderRequest;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends AbstractCrudServiceImpl<Order>
        implements OrderService {

    @Autowired
    private StockService stockService;

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

        Long inStock = stockService.countProductInStock (ean);

        if (qty > inStock) {
            orderEntry.setQuantity (inStock);
        }
    }

    private void addEntry (OrderEntry orderEntry, Order order) {
        List<OrderEntry> entries = order.getEntries ();
        entries.add (orderEntry);
        order.setEntries (entries);
        super.save (order);
    }

    private void afterAddEntry (OrderEntry orderEntry) {
        String ean = orderEntry.getProductEan ();
        Long value = orderEntry.getQuantity ();
        stockService.reduceStock (ean, value);
    }
}
