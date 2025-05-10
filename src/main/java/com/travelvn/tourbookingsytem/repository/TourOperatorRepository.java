package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourOperatorRepository extends JpaRepository<TourOperator, Integer> {

}
