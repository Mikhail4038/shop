package com.keiko.orderservice.event.listener;

import com.keiko.commonservice.entity.resource.product.Product;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.service.resources.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RecalculateOrderListener {

    @Autowired
    private ProductService productService;

    @EventListener
    public void onApplicationEvent (RecalculateOrderEvent event) {
        Order order = event.getOrder ();

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderEntry entry : order.getEntries ()) {
            String ean = entry.getProductEan ();
            Product product = productService.findByEan (ean);
            totalPrice = totalPrice.add (product.getPrice ().getValue ().multiply (BigDecimal.valueOf (entry.getQuantity ())));
        }
        order.setTotalPrice (totalPrice);
    }
}
