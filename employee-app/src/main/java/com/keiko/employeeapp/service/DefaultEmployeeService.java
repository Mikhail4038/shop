package com.keiko.employeeapp.service;

import com.keiko.employeeapp.entity.Employee;
import com.keiko.employeeapp.repository.EmployeeRepository;
import com.keiko.employeeapp.request.AddressRequest;
import com.keiko.employeeapp.request.EmployeeRequest;
import com.keiko.employeeapp.response.AddressResponse;
import com.keiko.employeeapp.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DefaultAddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeResponse save (EmployeeRequest employeeRequest) {

        Employee employee = modelMapper.map (employeeRequest, Employee.class);
        AddressRequest addressRequest = employeeRequest.getAddressRequest ();
        Employee saved = employeeRepository.save (employee);
        addressRequest.setEmployeeId (saved.getId ());

        EmployeeResponse employeeResponse =
                modelMapper.map (saved, EmployeeResponse.class);

        AddressResponse addressResponse = addressService.saveEmployeeAddress (addressRequest);
        employeeResponse.setAddressResponse (addressResponse);
        return employeeResponse;
    }

    public EmployeeResponse fetchBy (Long id) {
        Employee employee = employeeRepository.findById (id).orElseThrow ();
        EmployeeResponse response = modelMapper.map (employee, EmployeeResponse.class);
        AddressResponse addressResponse = addressService.findAddressByEmployeeId (id);
        response.setAddressResponse (addressResponse);
        return response;
    }
}
