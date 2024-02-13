package com.keiko.orderservice.controller;

import com.keiko.orderservice.dto.model.order.OrderDto;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.request.ModificationOrderRequest;
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

    @GetMapping (value = CREATE_ORDER + "/{userId}/{shopId}")
    public ResponseEntity createOrder (@PathVariable Long userId,
                                       @PathVariable Long shopId) {
        orderService.createOrder (userId, shopId);
        return ResponseEntity.status (CREATED).build ();
    }

    @PostMapping (value = SAVE_ORDER_ENTRY)
    public ResponseEntity saveOrderEntry (@RequestBody ModificationOrderRequest saveOrderEntryRequest) {
        orderService.saveOrderEntry (saveOrderEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = REMOVE_ORDER_ENTRY)
    public ResponseEntity removeOrderEntry (@RequestBody ModificationOrderRequest removeOrderEntryRequest) {
        orderService.removeOrderEntry (removeOrderEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = SAVE_DELIVERY_ADDRESS + "/{orderId}")
    public ResponseEntity saveDeliveryAddress (@RequestBody DeliveryAddress deliveryAddress,
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
