package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.CompanionCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanionCustomerRepository extends JpaRepository<CompanionCustomer,Integer> {
}
