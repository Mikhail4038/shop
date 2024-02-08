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

        Double totalPrice = 0.0;
        for (OrderEntry entry : order.getEntries ()) {
            String ean = entry.getProductEan ();
            Product product = productService.findByEan (ean);
            totalPrice += product.getPrice ().getValue () * entry.getQuantity ();
        }
        order.setTotalPrice (totalPrice);
    }
}
