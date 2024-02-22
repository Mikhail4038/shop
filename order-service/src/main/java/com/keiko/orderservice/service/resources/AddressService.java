package com.keiko.orderservice.service.resources;

import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.commonservice.request.RouteDetailsRequest;
import com.keiko.commonservice.response.RouteDetailsResponse;
import com.keiko.orderservice.entity.DeliveryAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.commonservice.constants.MicroServiceConstants.ADDRESS_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.ADDRESS_BASE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.CALCULATE_ROUTE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.REVERSE_GEOCODE;

@Service
@FeignClient (name = ADDRESS_SERVICE)
public interface AddressService {

    @PostMapping (value = ADDRESS_BASE + CALCULATE_ROUTE)
    RouteDetailsResponse calculateRoute (@RequestBody RouteDetailsRequest routeDetailsRequest);

    @PostMapping (value = ADDRESS_BASE + REVERSE_GEOCODE)
    DeliveryAddress reverseGeocode (@RequestBody ReverseGeocodeRequest reverseGeocodeRequest);
}
