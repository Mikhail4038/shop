package com.keiko.addressservice.controller;

import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;
import com.keiko.addressservice.service.AddressService;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.commonservice.request.RouteDetailsRequest;
import com.keiko.commonservice.response.RouteDetailsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.addressservice.constants.WebResourceKeyConstants.ADDRESS_BASE;
import static com.keiko.addressservice.constants.WebResourceKeyConstants.GEOCODE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.CALCULATE_ROUTE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.REVERSE_GEOCODE;

@Slf4j
@RestController
@RequestMapping (value = ADDRESS_BASE)
@Tag (name = "Address API")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // address to coordinate
    @PostMapping (value = GEOCODE)
    public ResponseEntity<GeocodeResponse> geocode (@RequestBody GeocodeRequest geocodeRequest) {
        GeocodeResponse response = addressService.geocode (geocodeRequest);
        return ResponseEntity.ok (response);
    }

    //coordinate to address
    @PostMapping (value = REVERSE_GEOCODE)
    public ResponseEntity<ReverseGeocodeResponse> reverseGeocode (@RequestBody ReverseGeocodeRequest reverseGeocodeRequest) {
        ReverseGeocodeResponse response = addressService.reverseGeocode (reverseGeocodeRequest);
        return ResponseEntity.ok (response);
    }

    @PostMapping (value = CALCULATE_ROUTE)
    public ResponseEntity<RouteDetailsResponse> calculateRoute (@RequestBody RouteDetailsRequest routeDetailsRequest) {
        RouteDetailsResponse detailsResponse = addressService.calculateRoute (routeDetailsRequest);
        return ResponseEntity.ok (detailsResponse);
    }
}
