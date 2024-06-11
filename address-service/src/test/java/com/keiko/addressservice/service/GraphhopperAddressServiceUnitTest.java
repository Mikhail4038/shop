package com.keiko.addressservice.service;

import com.keiko.addressservice.exception.model.GeocodingProcessException;
import com.keiko.addressservice.properties.GraphhopperProperties;
import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.service.impl.GraphhopperAddressService;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static com.keiko.addressservice.util.TestData.createTestGeoCodeRequest;
import static com.keiko.addressservice.util.TestData.createTestReverseGeocodeRequest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
public class GraphhopperAddressServiceUnitTest {

    private static final String API_RESOURCE = "http:/resource.com";
    private static final String API_KEY = "key";

    @Mock
    private GraphhopperProperties graphhopperProperties;

    @Mock
    private OkHttpClient okHttpClient;

    @Mock
    private Call call;

    @InjectMocks
    private static GraphhopperAddressService addressService;

    private static GeocodeRequest geocodeRequest;
    private static ReverseGeocodeRequest reverseGeocodeRequest;

    @BeforeAll
    static void setUp () {
        addressService = new GraphhopperAddressService ();
        geocodeRequest = createTestGeoCodeRequest ();
        reverseGeocodeRequest = createTestReverseGeocodeRequest ();
    }

    @BeforeEach
    void setUpApi () {
        when (graphhopperProperties.getApiResource ()).thenReturn (API_RESOURCE);
        when (graphhopperProperties.getApiKey ()).thenReturn (API_KEY);
        addressService.init ();
    }

    @Test
    void should_unSuccessfully_geocode_exception_response_process () throws IOException {
        when (okHttpClient.newCall (any (Request.class))).thenReturn (call);
        when (call.execute ()).thenThrow (IOException.class);

        assertThrows (GeocodingProcessException.class,
                () -> addressService.geocode (geocodeRequest));

        verify (okHttpClient, times (1)).newCall (any (Request.class));
        verifyNoMoreInteractions (okHttpClient);
        verify (call, times (1)).execute ();
        verifyNoMoreInteractions (call);
    }

    @Test
    void should_unSuccessfully_reverse_geocode_exception_response_process () throws IOException {
        when (okHttpClient.newCall (any (Request.class))).thenReturn (call);
        when (call.execute ()).thenThrow (IOException.class);

        assertThrows (GeocodingProcessException.class,
                () -> addressService.reverseGeocode (reverseGeocodeRequest));

        verify (okHttpClient, times (1)).newCall (any (Request.class));
        verifyNoMoreInteractions (okHttpClient);
        verify (call, times (1)).execute ();
        verifyNoMoreInteractions (call);
    }
}
