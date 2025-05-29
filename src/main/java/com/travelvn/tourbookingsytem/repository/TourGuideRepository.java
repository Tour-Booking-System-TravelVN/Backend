package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourGuideRepository extends JpaRepository<TourGuide, Integer> {
    List<TourGuide> findByFirstnameContainingIgnoreCase(String firstname);
    List<TourGuide> findByLastnameContainingIgnoreCase(String lastname);
    List<TourGuide> findByFirstnameContainingIgnoreCaseAndLastnameContainingIgnoreCase(String firstname, String lastname);
}
