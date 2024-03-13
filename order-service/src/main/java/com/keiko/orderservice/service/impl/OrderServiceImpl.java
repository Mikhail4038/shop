package com.keiko.orderservice.service.impl;

import com.keiko.commonservice.entity.resource.Address;
import com.keiko.commonservice.entity.resource.Shop;
import com.keiko.commonservice.request.RouteDetailsRequest;
import com.keiko.commonservice.request.StockOrderEntryRequest;
import com.keiko.commonservice.response.RouteDetailsResponse;
import com.keiko.commonservice.service.impl.DefaultCrudServiceImpl;
import com.keiko.orderservice.dto.model.OrderDto;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.OrderDetailsEmail;
import com.keiko.orderservice.exception.model.OrderProcessException;
import com.keiko.orderservice.repository.OrderRepository;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.AddressService;
import com.keiko.orderservice.service.resources.PaypalService;
import com.keiko.orderservice.service.resources.ShopService;
import com.keiko.orderservice.service.resources.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Service
public class OrderServiceImpl extends DefaultCrudServiceImpl<Order>
        implements OrderService {

    private static final Integer KILOMETER_BASE_RATE = 3;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaypalService paymentService;

    @Autowired
    private KafkaTemplate<Long, OrderDetailsEmail> kafkaTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Function<Order, OrderDto> toDtoConverter;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrder (Long userId, Long shopId) {
        Order order = new Order ();
        order.setUserId (userId);
        order.setShopId (shopId);
        order.setOrderStatus (OrderStatus.CREATED);
        super.save (order);
    }

    @Override
    public void cancelOrder (Long orderId) {
        Order order = super.fetchBy (orderId);
        if (!isValidOrderStatus (order, OrderStatus.CREATED) &&
                !isValidOrderStatus (order, OrderStatus.PLACED)) {
            throw new OrderProcessException ("Order cannot be cancel, please check order status");
        }

        for (OrderEntry entry : order.getEntries ()) {
            StockOrderEntryRequest cancelBookEntryRequest =
                    StockOrderEntryRequest.builder ()
                            .ean (entry.getProductEan ())
                            .quantity (entry.getQuantity ())
                            .shopId (order.getShopId ())
                            .build ();
            shopService.cancelBookStock (cancelBookEntryRequest);
        }
        order.setOrderStatus (OrderStatus.CANCELLED);
        super.save (order);
    }

    @Override
    @Transactional
    public void placeOrder (Long orderId) {
        Order order = super.fetchBy (orderId);
        if (!isValidOrderStatus (order, OrderStatus.CREATED)) {
            throw new OrderProcessException ("Order cannot be placed, please check order status");
        }
        ;
        sellProductStocks (order);
        calculateFinalSumOrder (order);
        sendOrderDetailsEmail (order);
        order.setOrderStatus (OrderStatus.PLACED);
        super.save (order);
    }

    @Override
    public Order fetchByPayId (String payId) {
        return orderRepository.findByPayId (payId).orElseThrow ();
    }

    @Override
    public List<Order> fetchByStatus (OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus (orderStatus);
    }

    private void sellProductStocks (Order order) {
        List<OrderEntry> entries = order.getEntries ();
        Long shopId = order.getShopId ();

        for (OrderEntry entry : entries) {
            StockOrderEntryRequest sellEntryRequest =
                    StockOrderEntryRequest.builder ()
                            .ean (entry.getProductEan ())
                            .quantity (entry.getQuantity ())
                            .shopId (shopId)
                            .build ();
            shopService.sellProductStocks (sellEntryRequest);
        }
    }

    private void calculateFinalSumOrder (Order order) {
        calculateDeliveryCost (order);
        calculateTotalAmount (order);
    }

    private void calculateDeliveryCost (Order order) {
        Shop shop = shopService.fetchBy (order.getShopId ());
        Address shopAddress = modelMapper.map (shop.getShopAddress (), Address.class);
        Address deliveryAddress = modelMapper.map (order.getDeliveryAddress (), Address.class);

        RouteDetailsRequest routeDetailsRequest =
                RouteDetailsRequest.builder ()
                        .from (shopAddress)
                        .to (deliveryAddress)
                        .build ();

        RouteDetailsResponse routeDetailsResponse = addressService.calculateRoute (routeDetailsRequest);
        Double deliveryDistance = routeDetailsResponse.getDistance ();
        Double deliveryCost = deliveryDistance * KILOMETER_BASE_RATE;
        order.setDeliveryCost (BigDecimal.valueOf (deliveryCost));
    }

    private void calculateTotalAmount (Order order) {
        BigDecimal totalPrice = order.getTotalPrice ();
        BigDecimal deliveryCost = order.getDeliveryCost ();
        order.setTotalAmount (totalPrice.add (deliveryCost));
    }

    private void sendOrderDetailsEmail (Order order) {
        String email = userService.fetchBy (order.getUserId ()).getEmail ();
        OrderDto dto = toDtoConverter.apply (order);
        OrderDetailsEmail orderDetailsEmail = OrderDetailsEmail.builder ()
                .toAddress (email)
                .subject ("Thank you for placing the order")
                .message ("Order details: ")
                .order (dto)
                .build ();
        kafkaTemplate.send ("orderDetails", orderDetailsEmail);
    }

    private boolean isValidOrderStatus (Order order, OrderStatus status) {
        OrderStatus orderStatus = order.getOrderStatus ();
        return orderStatus.equals (status);
    }
}
