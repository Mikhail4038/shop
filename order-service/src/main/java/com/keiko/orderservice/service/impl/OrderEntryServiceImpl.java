package com.keiko.orderservice.service.impl;

import com.keiko.commonservice.request.StockOrderEntryRequest;
import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.exception.model.OrderProcessException;
import com.keiko.orderservice.request.OrderEntryRequest;
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
    private DefaultCrudService<Order> orderService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void saveOrderEntry (OrderEntryRequest saveEntryRequest) {
        String ean = saveEntryRequest.getProductEan ();
        Long qty = saveEntryRequest.getQuantity ();
        Long orderId = saveEntryRequest.getOrderId ();

        Order order = orderService.fetchBy (orderId);
        checkOrderStatus (order);
        Long shopId = order.getShopId ();

        qty = beforeSaveOrderEntry (ean, shopId, qty);
        OrderEntry orderEntry = createOrGetOrderEntry (ean, qty, order);
        saveOrderEntry (orderEntry, order);
        afterSaveOrderEntry (buildBookEntryRequest (ean, qty, shopId));
    }

    @Override
    @Transactional
    public void removeOrderEntry (OrderEntryRequest removeEntryRequest) {
        String ean = removeEntryRequest.getProductEan ();
        Long qty = removeEntryRequest.getQuantity ();
        Long orderId = removeEntryRequest.getOrderId ();

        Order order = orderService.fetchBy (orderId);
        checkOrderStatus (order);
        Long shopId = order.getShopId ();

        OrderEntry orderEntry = getOrderEntry (ean, order);
        removeOrChangeQtyOrderEntry (qty, orderEntry, order);
        afterRemoveOrderEntry (buildBookEntryRequest (ean, qty, shopId));
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

    private void afterSaveOrderEntry (StockOrderEntryRequest bookEntryRequest) {
        shopService.bookStock (bookEntryRequest);
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

    private void afterRemoveOrderEntry (StockOrderEntryRequest cancelBookEntryRequest) {
        shopService.cancelBookStock (cancelBookEntryRequest);
    }

    private StockOrderEntryRequest buildBookEntryRequest (String ean, Long qty, Long shopId) {
        return StockOrderEntryRequest.builder ()
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
