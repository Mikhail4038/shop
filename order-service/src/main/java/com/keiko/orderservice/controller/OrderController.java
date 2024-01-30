package com.keiko.orderservice.controller;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.request.AddEntryToOrderRequest;
import com.keiko.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.orderservice.constants.WebResourceKeyConstants.ADD_ENTRY_TO_ORDER;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.ORDER_BASE;

@RestController
@RequestMapping (value = ORDER_BASE)
public class OrderController extends AbstractCrudController<Order> {

    @Autowired
    private OrderService orderService;

    @PostMapping (value = ADD_ENTRY_TO_ORDER)
    public ResponseEntity addOrderEntry (@RequestBody AddEntryToOrderRequest request) {
        orderService.addOrderEntry (request);
        return ResponseEntity.ok ().build ();
    }
}
