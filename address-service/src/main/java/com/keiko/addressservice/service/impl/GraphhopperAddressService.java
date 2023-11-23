package com.keiko.addressservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keiko.addressservice.exception.model.GeocodingProcessException;
import com.keiko.addressservice.properties.GraphhopperProperties;
import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.request.ReverseGeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;
import com.keiko.addressservice.service.AddressService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GraphhopperAddressService implements AddressService {

    @Autowired
    private GraphhopperProperties graphhopperProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OkHttpClient httpClient;

    private String resource;
    private String key;

    @PostConstruct
    public void init () {
        resource = graphhopperProperties.getApiResource ();
        key = graphhopperProperties.getApiKey ();
    }

    @Override
    public GeocodeResponse geocode (@NonNull GeocodeRequest geocodingRequest) {
        String url = buildGeocodeUrl (geocodingRequest);
        Request request = new Request.Builder ()
                .url (url)
                .get ()
                .build ();

        Response response = null;
        GeocodeResponse geocodeResponse = null;
        try {
            response = httpClient.newCall (request).execute ();
            geocodeResponse = buildGeocodeResponse (response);
        } catch (IOException ex) {
            String message = String.format ("Exception during address geocode, reason: %s", ex.getMessage ());
            throw new GeocodingProcessException (message);
        }
        return geocodeResponse;
    }

    @Override
    public ReverseGeocodeResponse reverseGeocode (@NonNull ReverseGeocodeRequest reverseGeocodeRequest) {
        String url = buildReverseGeocodeUrl (reverseGeocodeRequest);
        Request request = new Request.Builder ()
                .url (url)
                .get ()
                .build ();

        Response response = null;
        ReverseGeocodeResponse reverseGeocodeResponse = null;
        try {
            response = httpClient.newCall (request).execute ();
            reverseGeocodeResponse = buildReverseGeocodeResponse (response);
        } catch (IOException ex) {
            String message = String.format ("Exception during address reverse geocode, reason: %s", ex.getMessage ());
            throw new GeocodingProcessException (message);
        }
        return reverseGeocodeResponse;
    }

    private String buildGeocodeUrl (GeocodeRequest geocodingRequest) {
        final String street = geocodingRequest.getStreet ();
        final String house = geocodingRequest.getHouse ();
        final String city = geocodingRequest.getCity ();
        final String country = geocodingRequest.getCountry ();
        final String locale = geocodingRequest.getLocale ();
        final String addressData = street + " " + house + ", " + city + ", " + country;
        final String url = String.format ("%s?q=%s&locale=%s&key=%s", resource, addressData, locale, key);
        return url;
    }

    private GeocodeResponse buildGeocodeResponse (Response response) throws IOException {
        GeocodeResponse geocodeResponse = new GeocodeResponse ();

        JsonNode json = objectMapper.readTree (response.body ().string ());
        JsonNode hits = json.get ("hits").get (0);
        JsonNode point = hits.get ("point");
        String lat = point.get ("lat").asText ();
        String lng = point.get ("lng").asText ();

        geocodeResponse.setLat (lat);
        geocodeResponse.setLng (lng);

        geocodeResponse.setLat (lat);
        geocodeResponse.setLng (lng);
        return geocodeResponse;
    }

    private String buildReverseGeocodeUrl (ReverseGeocodeRequest reverseGeocodeRequest) {
        final String lat = reverseGeocodeRequest.getLat ();
        final String lng = reverseGeocodeRequest.getLng ();
        final String url = String.format ("%s?reverse=true&point=%s,%s&key=%s", resource, lat, lng, key);
        return url;
    }

    private ReverseGeocodeResponse buildReverseGeocodeResponse (Response response) throws IOException {

        JsonNode json = objectMapper.readTree (response.body ().string ());
        JsonNode hits = json.get ("hits").get (0);
        final String street = hits.get ("name").asText ();
        final String house = hits.get ("housenumber").asText ();
        final String city = hits.get ("city").asText ();
        final String country = hits.get ("country").asText ();
        final String postcode = hits.get ("postcode").asText ();
        ReverseGeocodeResponse reverseGeocodeResponse = new ReverseGeocodeResponse ();
        reverseGeocodeResponse.setStreet (street);
        reverseGeocodeResponse.setHouse (house);
        reverseGeocodeResponse.setCity (city);
        reverseGeocodeResponse.setCountry (country);
        reverseGeocodeResponse.setPostcode (postcode);
        return reverseGeocodeResponse;
    }
}
