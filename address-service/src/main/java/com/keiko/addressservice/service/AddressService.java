package com.keiko.addressservice.service;

import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.request.ReverseGeocodeRequest;
import com.keiko.addressservice.request.RouteDetailsRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;
import com.keiko.addressservice.response.RouteDetailsResponse;
import lombok.NonNull;

public interface AddressService {

    GeocodeResponse geocode (@NonNull GeocodeRequest geocodeRequest);

    ReverseGeocodeResponse reverseGeocode (@NonNull ReverseGeocodeRequest reverseGeocodeRequest);

    RouteDetailsResponse calculateRoute (@NonNull RouteDetailsRequest routeDetailsRequest);
}
