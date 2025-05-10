package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.CustomerAdRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerAdResponse;
import com.travelvn.tourbookingsytem.mapper.CustomerAdMapper;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerAdService {

    private CustomerRepository customerRepository;
    private CustomerAdMapper customerAdMapper;
    @Autowired
    private UserAccountAdService userAccountAdService;



    public CustomerAdService(CustomerRepository customerRepository, CustomerAdMapper customerAdMapper) {
        this.customerRepository = customerRepository;
        this.customerAdMapper = customerAdMapper;
    }

    public List<CustomerAdResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerAdMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerAdResponse getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(customerAdMapper::toResponse).orElse(null);
    }

    public List<CustomerAdResponse> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllCustomers();
        }

        List<Customer> byFirstOrLastName = customerRepository.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(name, name);
        List<Customer> byFullname = customerRepository.findByFullnameContainingIgnoreCase(name);

        return Stream.concat(byFirstOrLastName.stream(), byFullname.stream())
                .distinct()
                .map(customerAdMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerAdResponse createCustomer(CustomerAdRequest customerAdRequest) {
        Customer customer = customerAdMapper.toEntity(customerAdRequest);
        customer.setId(null); // Đảm bảo ID tự sinh
        Customer savedCustomer = customerRepository.save(customer);
        userAccountAdService.createUserAccountForCustomer(savedCustomer);
        return customerAdMapper.toResponse(savedCustomer);
    }

    public CustomerAdResponse updateCustomer(Integer id, CustomerAdRequest customerAdRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customerAdMapper.updateEntityFromRequest(customerAdRequest, customer);
            Customer updatedCustomer = customerRepository.save(customer);
            return customerAdMapper.toResponse(updatedCustomer);
        }
        return null;
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}