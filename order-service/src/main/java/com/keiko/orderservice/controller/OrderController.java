package com.keiko.orderservice.controller;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.request.UpgradeOrderRequest;
import com.keiko.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = ORDER_BASE)
public class OrderController extends AbstractCrudController<Order> {

    @Autowired
    private OrderService orderService;

    @PostMapping (value = SAVE_ORDER_ENTRY)
    public ResponseEntity saveOrderEntry (@RequestBody UpgradeOrderRequest request) {
        orderService.saveOrderEntry (request);
        return ResponseEntity.ok ().build ();
    }


    @PostMapping (value = REMOVE_ORDER_ENTRY)
    public ResponseEntity removeOrderEntry (@RequestBody UpgradeOrderRequest request) {
        orderService.removeOrderEntry (request);
        return ResponseEntity.ok ().build ();
    }
}
