package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.request.BookingStockRequest;
import com.keiko.orderservice.request.UpgradeOrderRequest;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.StockService;
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
    private ApplicationEventPublisher eventPublisher;

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
        afterSaveEntry (ean, qty);
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

        stockService.cancelBookedStock (new BookingStockRequest (ean, qty));
    }

    @Override
    public void placeOrder (Order order) {

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

    private void afterSaveEntry (String ean, Long qty) {
        stockService.bookedStock (new BookingStockRequest (ean, qty));
    }
}
