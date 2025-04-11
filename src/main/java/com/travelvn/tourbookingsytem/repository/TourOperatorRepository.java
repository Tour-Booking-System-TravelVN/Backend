package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourOperatorRepository extends JpaRepository<TourOperator, Integer> {
}
