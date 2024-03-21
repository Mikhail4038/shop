package com.keiko.orderservice.controller;

import com.keiko.commonservice.controller.DefaultCrudController;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.orderservice.dto.model.OrderDto;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.request.OrderEntryRequest;
import com.keiko.orderservice.service.DeliveryAddressService;
import com.keiko.orderservice.service.OrderEntryService;
import com.keiko.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.ORDER_BASE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping (value = ORDER_BASE)
@Tag (name = "Orders API")
public class OrderController extends DefaultCrudController<Order, OrderDto> {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private OrderEntryService orderEntryService;

    @GetMapping (value = CREATE_ORDER + "/{userId}/{shopId}")
    public ResponseEntity createOrder (@PathVariable Long userId,
                                       @PathVariable Long shopId) {
        orderService.createOrder (userId, shopId);
        return ResponseEntity.status (CREATED).build ();
    }

    @GetMapping (value = CANCEL_ORDER)
    public ResponseEntity cancelOrder (@RequestParam Long orderId) {
        orderService.cancelOrder (orderId);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = SAVE_ORDER_ENTRY)
    public ResponseEntity saveOrderEntry (@RequestBody OrderEntryRequest saveEntryRequest) {
        orderEntryService.saveOrderEntry (saveEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = REMOVE_ORDER_ENTRY)
    public ResponseEntity removeOrderEntry (@RequestBody OrderEntryRequest removeEntryRequest) {
        orderEntryService.removeOrderEntry (removeEntryRequest);
        return ResponseEntity.ok ().build ();
    }

    @Operation (summary = "Save delivery address",
            description = "Use new delivery address")
    @PostMapping (value = SAVE_DELIVERY_ADDRESS + "/{orderId}")
    public ResponseEntity saveDeliveryAddress (@RequestBody DeliveryAddress deliveryAddress,
                                               @PathVariable Long orderId) {
        deliveryAddressService.saveDeliveryAddress (deliveryAddress, orderId);
        return ResponseEntity.ok ().build ();
    }

    @Operation (summary = "Save delivery address",
            description = "Use user address")
    @GetMapping (value = SAVE_DELIVERY_ADDRESS + "/{orderId}")
    public ResponseEntity userAddressToDeliveryAddress (@PathVariable Long orderId) {
        deliveryAddressService.saveDeliveryAddress (orderId);
        return ResponseEntity.ok ().build ();
    }

    @Operation (summary = "Save delivery address",
            description = "Point delivery address at map")
    @PostMapping (value = POINT_DELIVERY_ADDRESS + "/{orderId}")
    public ResponseEntity pointAtMap (@RequestBody ReverseGeocodeRequest reverseGeocodeRequest,
                                      @PathVariable Long orderId) {
        deliveryAddressService.saveDeliveryAddress (reverseGeocodeRequest, orderId);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = PLACE_ORDER)
    public ResponseEntity placeOrder (@RequestParam Long orderId) {
        orderService.placeOrder (orderId);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = FIND_BY_STATUS)
    public ResponseEntity<List<OrderDto>> fetchByStatus (@RequestParam OrderStatus status) {
        List<Order> orders = orderService.fetchByStatus (status);
        List<OrderDto> dto = orders.stream ()
                .map (getToDtoConverter ()::apply)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }

    @Override
    public ResponseEntity save (OrderDto dto) {
        throw new UnsupportedOperationException ("For create new order use only factory method");
    }
}
