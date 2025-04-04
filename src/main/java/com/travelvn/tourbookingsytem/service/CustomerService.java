package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerResponse;
import com.travelvn.tourbookingsytem.mapper.CustomerMapper;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true)
public class CustomerService {

    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper){
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Lấy thông tin khách hàng theo ID
    public Customer getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    // Thêm khách hàng mới
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Cập nhật thông tin khách hàng
    public Customer updateCustomer(Integer id, Customer customerDetails) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setFirstname(customerDetails.getFirstname());
            customer.setLastname(customerDetails.getLastname());
            customer.setDateOfBirth(customerDetails.getDateOfBirth());
            customer.setGender(customerDetails.getGender());
            customer.setNationality(customerDetails.getNationality());
            customer.setCitizenId(customerDetails.getCitizenId());
            customer.setPassport(customerDetails.getPassport());
            customer.setPhoneNumber(customerDetails.getPhoneNumber());
            customer.setNote(customerDetails.getNote());
            customer.setAddress(customerDetails.getAddress());
            return customerRepository.save(customer);
        }
        return null;
    }

    // Xóa khách hàng
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
