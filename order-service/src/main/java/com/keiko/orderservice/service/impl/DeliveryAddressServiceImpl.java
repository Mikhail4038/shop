package com.keiko.orderservice.service.impl;

import com.keiko.commonservice.entity.resource.User;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.service.DeliveryAddressService;
import com.keiko.orderservice.service.resources.AddressService;
import com.keiko.orderservice.service.resources.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    @Autowired
    private DefaultCrudService<Order> orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveDeliveryAddress (DeliveryAddress deliveryAddress, Long orderId) {
        Order order = orderService.fetchBy (orderId);
        order.setDeliveryAddress (deliveryAddress);
        orderService.save (order);
    }

    @Override
    public void saveDeliveryAddress (ReverseGeocodeRequest reverseGeocodeRequest, Long orderId) {
        DeliveryAddress deliveryAddress = addressService.reverseGeocode (reverseGeocodeRequest);
        this.saveDeliveryAddress (deliveryAddress, orderId);


    }

    @Override
    public void saveDeliveryAddress (Long orderId) {
        Order order = orderService.fetchBy (orderId);
        Long userId = order.getUserId ();
        User user = userService.fetchBy (userId);
        DeliveryAddress deliveryAddress = modelMapper.map (user.getUserAddress (), DeliveryAddress.class);
        this.saveDeliveryAddress (deliveryAddress, orderId);
    }

}
