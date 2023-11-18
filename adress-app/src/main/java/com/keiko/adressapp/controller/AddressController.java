package com.keiko.adressapp.controller;

import com.keiko.adressapp.entity.Address;
import com.keiko.adressapp.request.AddressRequest;
import com.keiko.adressapp.response.AddressResponse;
import com.keiko.adressapp.service.DefaultAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/address")
public class AddressController {

    @Autowired
    private DefaultAddressService addressService;

    @PostMapping ("/save")
    public ResponseEntity save (@RequestBody AddressRequest addressRequest) {
        addressService.save (addressRequest);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping ("/fetchBy/{id}")
    public ResponseEntity<AddressResponse> fetchBy (@PathVariable Long id) {
        AddressResponse response = addressService.fetchBy (id);
        return ResponseEntity.status (HttpStatus.OK).body (response);
    }
}
