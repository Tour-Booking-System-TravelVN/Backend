package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
