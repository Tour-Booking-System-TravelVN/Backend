package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT c FROM Customer c WHERE c.firstname = :firstname AND c.lastname = :lastname AND c.dateOfBirth = :dob AND c.gender = :gender")
    Customer findCustomerByInfo(
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("dob") LocalDate dob,
            @Param("gender") Boolean gender
    );
}
