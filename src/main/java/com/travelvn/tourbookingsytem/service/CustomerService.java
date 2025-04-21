package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@FieldDefaults(makeFinal = true)
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
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
    // Tìm kiếm khách hàng theo tên (firstname, lastname, hoặc fullname)
    public List<Customer> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return customerRepository.findAll(); // Nếu không có tên tìm kiếm, trả về toàn bộ danh sách
        }

        // Tìm kiếm theo firstname hoặc lastname
        List<Customer> byFirstOrLastName = customerRepository.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(name, name);

        // Tìm kiếm theo fullname
        List<Customer> byFullname = customerRepository.findByFullnameContainingIgnoreCase(name);

        // Kết hợp kết quả và loại bỏ trùng lặp
        return Stream.concat(byFirstOrLastName.stream(), byFullname.stream())
                .distinct()
                .collect(Collectors.toList());
    }
    // Thêm khách hàng mới
    public Customer createCustomer(Customer customer) {
        // Đảm bảo id là null để database tự sinh
        customer.setId(null);
        // Lưu vào database, id sẽ được tự động sinh
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
            // Không cập nhật các trường quan hệ (userAccount, bookingSet, v.v.) vì không được gửi từ client
            return customerRepository.save(customer);
        }
        return null;
    }

    // Xóa khách hàng
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}