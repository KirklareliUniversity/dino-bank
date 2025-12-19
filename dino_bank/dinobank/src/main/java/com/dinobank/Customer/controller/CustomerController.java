package com.dinobank.Customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dinobank.Customer.dto.CustomerRequestDto;
import com.dinobank.Customer.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto> register(@RequestBody CustomerRequestDto request) {
        CustomerResponseDto response = customerService.createCustomer(request);
        return ResponseEntity.ok(response);
    }

    //admin paneli veya kontrol için
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}

/**
 * Burada CustomerController sınıfı oluşturulmuştur.
 * Bu sınıf, müşteri kayıtları için temel CRUD operasyonlarını gerçekleştirir.
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */