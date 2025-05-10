package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerResponse;
import com.travelvn.tourbookingsytem.mapper.CustomerMapper;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    @Autowired
    private UserAccountAdService userAccountAdService;



    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(customerMapper::toResponse).orElse(null);
    }

    public List<CustomerResponse> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllCustomers();
        }

        List<Customer> byFirstOrLastName = customerRepository.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(name, name);
        List<Customer> byFullname = customerRepository.findByFullnameContainingIgnoreCase(name);

        return Stream.concat(byFirstOrLastName.stream(), byFullname.stream())
                .distinct()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.toEntity(customerRequest);
        customer.setId(null); // Đảm bảo ID tự sinh
        Customer savedCustomer = customerRepository.save(customer);
        userAccountAdService.createUserAccountForCustomer(savedCustomer);
        return customerMapper.toResponse(savedCustomer);
    }

    public CustomerResponse updateCustomer(Integer id, CustomerRequest customerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customerMapper.updateEntityFromRequest(customerRequest, customer);
            Customer updatedCustomer = customerRepository.save(customer);
            return customerMapper.toResponse(updatedCustomer);
        }
        return null;
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}