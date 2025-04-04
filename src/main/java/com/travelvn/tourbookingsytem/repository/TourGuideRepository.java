package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourGuideRepository extends JpaRepository<TourGuide, Integer> {
    List<TourGuide> findByFirstnameAndLastname(String firstname, String lastname);
}
