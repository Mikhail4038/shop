package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.request.CalculatingRouteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.orderservice.constants.MicroServiceConstants.ADDRESS_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.ADDRESS_BASE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.CALCULATE_ROUTE;

@Service
@FeignClient (name = ADDRESS_SERVICE)
public interface AddressService {

    @PostMapping (value = ADDRESS_BASE + CALCULATE_ROUTE)
    Double calculateRoute (@RequestBody CalculatingRouteRequest calculatingRouteRequest);
}
