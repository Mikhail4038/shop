package com.keiko.orderservice.controller;

import com.keiko.orderservice.dto.model.order.OrderDto;
import com.keiko.orderservice.entity.Address;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.request.UpgradeOrderRequest;
import com.keiko.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping (value = ORDER_BASE)
public class OrderController extends AbstractCrudController<Order, OrderDto> {

    @Autowired
    private OrderService orderService;

    @GetMapping (value = CREATE_ORDER + "/{userId}")
    public ResponseEntity createOrder (@PathVariable Long userId) {
        orderService.createOrder (userId);
        return ResponseEntity.status (CREATED).build ();
    }

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

    @PostMapping (value = SAVE_DELIVERY_ADDRESS + "/{orderId}")
    public ResponseEntity saveDeliveryAddress (@RequestBody Address deliveryAddress,
                                               @PathVariable Long orderId) {
        orderService.saveDeliveryAddress (deliveryAddress, orderId);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = PLACE_ORDER)
    public ResponseEntity placeOrder (@RequestParam Long orderId) {
        orderService.placeOrder (orderId);
        return ResponseEntity.ok ().build ();
    }

    @Override
    public ResponseEntity save (OrderDto dto) {
        throw new UnsupportedOperationException ("For create new order use only factory method");
    }
}
