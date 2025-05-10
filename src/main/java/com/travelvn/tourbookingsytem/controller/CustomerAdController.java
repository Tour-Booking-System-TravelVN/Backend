package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.CustomerAdRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerAdResponse;
import com.travelvn.tourbookingsytem.service.CustomerAdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerAdController {

    @Autowired
    private CustomerAdService customerAdService;

    @GetMapping
    public ResponseEntity<List<CustomerAdResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerAdService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerAdResponse> getCustomerById(@PathVariable Integer id) {
        CustomerAdResponse customer = customerAdService.getCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerAdResponse>> searchCustomersByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(customerAdService.searchCustomersByName(name));
    }

    @PostMapping
    public ResponseEntity<CustomerAdResponse> createCustomer(@Valid @RequestBody CustomerAdRequest customerAdRequest) {
        return ResponseEntity.ok(customerAdService.createCustomer(customerAdRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerAdResponse> updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerAdRequest customerAdRequest) {
        CustomerAdResponse updatedCustomer = customerAdService.updateCustomer(id, customerAdRequest);
        return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerAdService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}