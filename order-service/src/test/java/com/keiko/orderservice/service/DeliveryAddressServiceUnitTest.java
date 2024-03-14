package com.keiko.orderservice.service;

import com.keiko.commonservice.entity.resource.user.User;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.service.impl.DeliveryAddressServiceImpl;
import com.keiko.orderservice.service.resources.AddressService;
import com.keiko.orderservice.service.resources.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.keiko.orderservice.util.TestData.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class DeliveryAddressServiceUnitTest {

    @Mock
    private DefaultCrudService<Order> orderService;

    @Mock
    private AddressService addressService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DeliveryAddressServiceImpl deliveryAddressService;

    private static Order order;
    private static User user;
    private static DeliveryAddress deliveryAddress;
    private static ReverseGeocodeRequest reverseGeocodeRequest;

    @BeforeAll
    static void setUp () {
        order = testOrder ();
        user = testUser ();
        deliveryAddress = testDeliveryAddress ();
        reverseGeocodeRequest = testReverseGeocodeRequest ();
    }

    @Test
    void should_successfully_saveDeliveryAddress () {
        when (orderService.fetchBy (ORDER_ID)).thenReturn (order);

        deliveryAddressService.saveDeliveryAddress (deliveryAddress, ORDER_ID);

        verify (orderService, times (1)).fetchBy (anyLong ());
        verify (orderService, times (1)).save (any (Order.class));

        InOrder orderCall = Mockito.inOrder (orderService);
        orderCall.verify (orderService).fetchBy (anyLong ());
        orderCall.verify (orderService).save (any (Order.class));
    }

    @Test
    void should_successfully_saveDeliveryAddressByGeocode () {
        when (addressService.reverseGeocode (reverseGeocodeRequest)).thenReturn (deliveryAddress);
        when (orderService.fetchBy (ORDER_ID)).thenReturn (order);

        deliveryAddressService.saveDeliveryAddress (reverseGeocodeRequest, ORDER_ID);

        verify (addressService, times (1)).reverseGeocode (any (ReverseGeocodeRequest.class));
    }

    @Test
    void should_successfully_saveDeliveryByUserAddress () {
        when (orderService.fetchBy (ORDER_ID)).thenReturn (order);
        when (userService.fetchBy (USER_ID)).thenReturn (user);
        when (modelMapper.map (user.getUserAddress (), DeliveryAddress.class)).thenReturn (deliveryAddress);

        deliveryAddressService.saveDeliveryAddress (ORDER_ID);

        verify (orderService, times (2)).fetchBy (anyLong ());
        verify (userService, times (1)).fetchBy (anyLong ());
        verify (modelMapper, times (1)).map (any (), any ());
    }

}
