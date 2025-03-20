package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Integer> {
}
