package com.keiko.addressservice.service;

import com.keiko.addressservice.request.CalculatingRouteRequest;
import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.request.ReverseGeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;
import lombok.NonNull;

public interface AddressService {

    GeocodeResponse geocode (@NonNull GeocodeRequest geocodingRequest);

    ReverseGeocodeResponse reverseGeocode (@NonNull ReverseGeocodeRequest reverseGeocodeRequest);

    Double calculateRoute (CalculatingRouteRequest calculatingRouteRequest);
}
