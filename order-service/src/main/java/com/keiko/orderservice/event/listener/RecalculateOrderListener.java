package com.keiko.orderservice.event.listener;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.resources.Product;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.service.resources.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RecalculateOrderListener {

    @Autowired
    private ProductService productService;

    @EventListener
    public void onApplicationEvent (RecalculateOrderEvent event) {
        Order order = event.getOrder ();

        Double totalAmount = 0.0;
        for (OrderEntry entry : order.getEntries ()) {
            Product product = productService.findByEan (entry.getProductEan ());
            Double price = product.getPrice ().getValue ();
            totalAmount += price * entry.getQuantity ();
        }

        order.setTotalAmount (totalAmount);
    }
}
