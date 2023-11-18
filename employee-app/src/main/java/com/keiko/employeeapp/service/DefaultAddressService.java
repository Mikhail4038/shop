package com.keiko.employeeapp.service;

import com.keiko.employeeapp.request.AddressRequest;
import com.keiko.employeeapp.response.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultAddressService {

    private static final String FIND_ADDRESS_BY_EMPL_ID_URL = "http://localhost:8081/address/fetchBy/{id}";
    private static final String SAVE_EMPL_ADDRESS_URL = "http://localhost:8081/address/save";


    @Autowired
    private RestTemplate restTemplate;

    public AddressResponse findAddressByEmployeeId (Long employeeId) {
        AddressResponse response
                = restTemplate.getForObject (FIND_ADDRESS_BY_EMPL_ID_URL, AddressResponse.class, employeeId);
        return response;
    }

    public AddressResponse saveEmployeeAddress (AddressRequest addressRequest) {
        AddressResponse response
                = restTemplate.postForObject (SAVE_EMPL_ADDRESS_URL, addressRequest, AddressResponse.class);
        return response;
    }
}
