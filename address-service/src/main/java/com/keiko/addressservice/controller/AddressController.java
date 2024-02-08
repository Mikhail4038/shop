package com.keiko.addressservice.controller;

import com.keiko.addressservice.request.CalculatingRouteRequest;
import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.request.ReverseGeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;
import com.keiko.addressservice.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.addressservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = ADDRESS_BASE)
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    // address to coordinate
    @PostMapping (value = GEOCODE)
    public ResponseEntity<GeocodeResponse> geocode (@RequestBody GeocodeRequest request) {
        GeocodeResponse response = addressService.geocode (request);
        return ResponseEntity.ok (response);
    }

    //coordinate to address
    @PostMapping (value = REVERSE_GEOCODE)
    public ResponseEntity<ReverseGeocodeResponse> reverseGeocode (@RequestBody ReverseGeocodeRequest request) {
        ReverseGeocodeResponse response = addressService.reverseGeocode (request);
        return ResponseEntity.ok (response);
    }

    @PostMapping (value = CALCULATE_ROUTE)
    public ResponseEntity<Double> calculateRoute (@RequestBody CalculatingRouteRequest calculatingRouteRequest) {
        Double route = addressService.calculateRoute (calculatingRouteRequest);
        return ResponseEntity.ok (1.0);
    }
}
