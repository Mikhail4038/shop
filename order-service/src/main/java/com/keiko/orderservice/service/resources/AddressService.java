package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.request.ReverseGeocodeRequest;
import com.keiko.orderservice.request.RouteDetailsRequest;
import com.keiko.orderservice.response.RouteDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.orderservice.constants.MicroServiceConstants.ADDRESS_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = ADDRESS_SERVICE)
public interface AddressService {

    @PostMapping (value = ADDRESS_BASE + CALCULATE_ROUTE)
    RouteDetailsResponse calculateRoute (@RequestBody RouteDetailsRequest routeDetailsRequest);

    @PostMapping (value = ADDRESS_BASE + REVERSE_GEOCODE)
    DeliveryAddress reverseGeocode (@RequestBody ReverseGeocodeRequest reverseGeocodeRequest);
}
