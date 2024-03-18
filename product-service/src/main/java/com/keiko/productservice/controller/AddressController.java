package com.keiko.productservice.controller;

import com.keiko.commonservice.controller.DefaultCrudController;
import com.keiko.productservice.dto.model.address.AddressDto;
import com.keiko.productservice.entity.Address;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.ADDRESS_BASE;

@RestController
@RequestMapping (value = ADDRESS_BASE)
public class AddressController extends DefaultCrudController<Address, AddressDto> {
}
