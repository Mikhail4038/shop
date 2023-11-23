package com.keiko.addressservice.util;

import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.request.ReverseGeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;

public class TestData {
    public static GeocodeRequest createTestGeoCodeRequest () {
        GeocodeRequest request = new GeocodeRequest ();
        request.setStreet ("street");
        request.setHouse ("house");
        request.setCity ("city");
        request.setCountry ("country");
        request.setLocale ("locale");
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
