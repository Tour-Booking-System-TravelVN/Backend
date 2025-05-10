package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourGuideRepository extends JpaRepository<TourGuide, Integer> {
    List<TourGuide> findByFirstnameContainingIgnoreCase(String firstname);
    List<TourGuide> findByLastnameContainingIgnoreCase(String lastname);
    List<TourGuide> findByFirstnameContainingIgnoreCaseAndLastnameContainingIgnoreCase(String firstname, String lastname);
}
