package com.keiko.addressservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keiko.addressservice.request.GeocodeRequest;
import com.keiko.addressservice.response.GeocodeResponse;
import com.keiko.addressservice.response.ReverseGeocodeResponse;
import com.keiko.addressservice.service.AddressService;
import com.keiko.commonservice.request.ReverseGeocodeRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.keiko.addressservice.constants.WebResourceKeyConstants.*;
import static com.keiko.addressservice.util.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    private static GeocodeRequest geocodeRequest;
    private static GeocodeResponse geocodeResponse;
    private static ReverseGeocodeRequest reverseGeocodeRequest;
    private static ReverseGeocodeResponse reverseGeocodeResponse;

    @BeforeAll
    static void setUp () {
        geocodeRequest = createTestGeoCodeRequest ();
        geocodeResponse = createTestGeoCodeResponse ();
        reverseGeocodeRequest = createTestReverseGeocodeRequest ();
        reverseGeocodeResponse = createTestReverseGeocodeResponse ();
    }

    @Test
    void address_geocode_should_successfully () throws Exception {
        when (addressService.geocode (geocodeRequest)).thenReturn (geocodeResponse);

        mockMvc.perform (post (ADDRESS_BASE + GEOCODE)
                .content (objectMapper.writeValueAsString (geocodeRequest))
                .contentType ("application/json"))
                .andExpect (jsonPath ("$.lat", is (geocodeResponse.getLat ())))
                .andExpect (jsonPath ("$.lng", is (geocodeResponse.getLng ())))
                .andExpect (status ().isOk ());

        verify (addressService, times (1)).geocode (any (GeocodeRequest.class));
        verifyNoMoreInteractions (addressService);
    }

    @Test
    void address_reverse_geocode_should_successfully () throws Exception {
        when (addressService.reverseGeocode (reverseGeocodeRequest)).thenReturn (reverseGeocodeResponse);

        mockMvc.perform (post (ADDRESS_BASE + REVERSE_GEOCODE)
                .content (objectMapper.writeValueAsString (reverseGeocodeRequest))
                .contentType ("application/json"))
                .andExpect (jsonPath ("$.street", is (reverseGeocodeResponse.getStreet ())))
                .andExpect (jsonPath ("$.house", is (reverseGeocodeResponse.getHouse ())))
                .andExpect (jsonPath ("$.city", is (reverseGeocodeResponse.getCity ())))
                .andExpect (jsonPath ("$.country", is (reverseGeocodeResponse.getCountry ())))
                .andExpect (jsonPath ("$.postcode", is (reverseGeocodeResponse.getPostcode ())))
                .andExpect (status ().isOk ());

        verify (addressService, times (1)).reverseGeocode (any (ReverseGeocodeRequest.class));
        verifyNoMoreInteractions (addressService);
    }
}
