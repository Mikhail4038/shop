package com.keiko.addressservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keiko.addressservice.exception.model.GeocodingProcessException;
import com.keiko.addressservice.exception.model.RouteProcessException;
import com.keiko.addressservice.properties.GraphhopperProperties;
import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;
import com.keiko.addressservice.service.AddressService;
import com.keiko.commonservice.entity.resource.Address;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.commonservice.request.RouteDetailsRequest;
import com.keiko.commonservice.response.RouteDetailsResponse;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GraphhopperAddressService implements AddressService {

    private static final String DEFAULT_PROFILE = "car";

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
    public GeocodeResponse geocode (@NonNull GeocodeRequest geocodeRequest) {
        String url = buildGeocodeUrl (geocodeRequest);
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

    @Override
    public RouteDetailsResponse calculateRoute (@NonNull RouteDetailsRequest routeDetailsRequest) {
        String url = buildRouteUrl (routeDetailsRequest);
        Request request = new Request.Builder ()
                .url (url)
                .get ()
                .build ();

        Response response = null;
        RouteDetailsResponse detailsResponse = null;
        try {
            response = httpClient.newCall (request).execute ();
            detailsResponse = buildRouteDetailsResponse (response);
        } catch (IOException ex) {
            String message = String.format ("Exception during route process, reason: %s", ex.getMessage ());
            throw new RouteProcessException (message);
        }
        return detailsResponse;
    }

    private String buildGeocodeUrl (GeocodeRequest geocodeRequest) {
        Address address = geocodeRequest.getAddress ();
        final String street = address.getStreet ();
        final String house = address.getHouse ();
        final String city = address.getCity ();
        final String country = address.getCountry ();
        final String addressData = street + " " + house + ", " + city + ", " + country;
        final String url = String.format ("%s/geocode?q=%s&key=%s", resource, addressData, key);
        return url;
    }

    private String buildReverseGeocodeUrl (ReverseGeocodeRequest reverseGeocodeRequest) {
        final String lat = reverseGeocodeRequest.getLat ();
        final String lng = reverseGeocodeRequest.getLng ();
        final String url = String.format ("%s/geocode?reverse=true&point=%s,%s&key=%s", resource, lat, lng, key);
        return url;
    }

    private String buildRouteUrl (RouteDetailsRequest routeDetailsRequest) {
        GeocodeResponse from = convertToGeocode (routeDetailsRequest.getFrom ());
        GeocodeResponse to = convertToGeocode (routeDetailsRequest.getTo ());
        String url = String.format (
                "%s/route?point=%s,%s&point=%s,%s&profile=%s&calc_points=%s&key=%s",
                resource, from.getLat (), from.getLng (), to.getLat (), to.getLng (), DEFAULT_PROFILE, false, key);
        return url;
    }

    private GeocodeResponse buildGeocodeResponse (Response response) throws IOException {
        JsonNode json = objectMapper.readTree (response.body ().string ());
        JsonNode hits = json.get ("hits").get (0);
        JsonNode point = hits.get ("point");
        String lat = point.get ("lat").asText ();
        String lng = point.get ("lng").asText ();

        GeocodeResponse geocodeResponse
                = GeocodeResponse.builder ()
                .lat (lat)
                .lng (lng)
                .build ();
        return geocodeResponse;
    }

    private ReverseGeocodeResponse buildReverseGeocodeResponse (Response response) throws IOException {
        JsonNode json = objectMapper.readTree (response.body ().string ());
        JsonNode hits = json.get ("hits").get (0);
        final String street = hits.get ("name").asText ();
        final String house = hits.get ("housenumber").asText ();
        final String city = hits.get ("city").asText ();
        final String country = hits.get ("country").asText ();
        final String postcode = hits.get ("postcode").asText ();

        ReverseGeocodeResponse reverseGeocodeResponse
                = ReverseGeocodeResponse.builder ()
                .street (street)
                .house (house)
                .city (city)
                .country (country)
                .postcode (postcode)
                .build ();
        return reverseGeocodeResponse;
    }

    private RouteDetailsResponse buildRouteDetailsResponse (Response response) throws IOException {
        JsonNode json = objectMapper.readTree (response.body ().string ());
        JsonNode paths = json.get ("paths").get (0);
        final Double distance = paths.get ("distance").asDouble ();
        final Double time = paths.get ("time").asDouble ();

        return RouteDetailsResponse.builder ()
                .distance (Precision.round (distance / 1000, 2))
                .time (Precision.round (time / 60000, 2))
                .build ();
    }

    private GeocodeResponse convertToGeocode (Address address) {
        GeocodeRequest geocodeRequest = new GeocodeRequest (address);
        GeocodeResponse geocodeResponse = this.geocode (geocodeRequest);
        return geocodeResponse;
    }

}
