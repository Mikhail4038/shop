package com.keiko.addressservice.util;

import com.keiko.addressservice.entity.Address;
import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.request.ReverseGeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;

public class TestData {
    public static GeocodeRequest createTestGeoCodeRequest () {
        GeocodeRequest request = new GeocodeRequest ();
        Address address = new Address ();
        address.setStreet ("street");
        address.setHouse ("house");
        address.setCity ("city");
        address.setCountry ("country");
        address.setLocale ("locale");
        request.setAddress (address);
        return request;
    }

    public static GeocodeResponse createTestGeoCodeResponse () {
        GeocodeResponse request = new GeocodeResponse ();
        request.setLat ("lat");
        request.setLng ("lng");
        return request;
    }

    public static ReverseGeocodeRequest createTestReverseGeocodeRequest () {
        ReverseGeocodeRequest request = new ReverseGeocodeRequest ();
        request.setLat ("lat");
        request.setLng ("lng");
        return request;
    }

    public static ReverseGeocodeResponse createTestReverseGeocodeResponse () {
        ReverseGeocodeResponse response = new ReverseGeocodeResponse ();
        response.setStreet ("street");
        response.setHouse ("house");
        response.setCity ("city");
        response.setCountry ("country");
        response.setPostcode ("postcode");
        return response;
    }
}
