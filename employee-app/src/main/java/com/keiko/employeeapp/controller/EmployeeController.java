package com.keiko.employeeapp.controller;

import com.keiko.employeeapp.entity.Employee;
import com.keiko.employeeapp.request.AddressRequest;
import com.keiko.employeeapp.request.EmployeeRequest;
import com.keiko.employeeapp.response.EmployeeResponse;
import com.keiko.employeeapp.service.DefaultEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/employee")
public class EmployeeController {

    @Autowired
    private DefaultEmployeeService employeeService;

    @PostMapping ("/save")
    public ResponseEntity<EmployeeResponse> save (
            @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.save (employeeRequest);
        return ResponseEntity.status (HttpStatus.OK).body (response);
    }

    @GetMapping ("/fetchBy/{id}")
    public ResponseEntity<EmployeeResponse> fetchBy (@PathVariable Long id) {
        EmployeeResponse response = employeeService.fetchBy (id);
        return ResponseEntity.status (HttpStatus.OK).body (response);
    }
}
