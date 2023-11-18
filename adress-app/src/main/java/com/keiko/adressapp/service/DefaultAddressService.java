package com.keiko.adressapp.service;

import com.keiko.adressapp.entity.Address;
import com.keiko.adressapp.repository.AddressRepository;
import com.keiko.adressapp.request.AddressRequest;
import com.keiko.adressapp.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;


    public void save (AddressRequest addressRequest) {
        final String city = addressRequest.getCity ();
        final String state = addressRequest.getState ();
        final Integer employeeId = addressRequest.getEmployeeId ().intValue ();

        addressRepository.save (city, state, employeeId);
    }

    public AddressResponse fetchBy (Long id) {
        Address address = addressRepository.findById (id).orElseThrow ();
        AddressResponse response = modelMapper.map (address, AddressResponse.class);
        return response;
    }
}
