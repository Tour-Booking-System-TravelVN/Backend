package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerResponse;
import com.travelvn.tourbookingsytem.mapper.CustomerMapper;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper){
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

}
