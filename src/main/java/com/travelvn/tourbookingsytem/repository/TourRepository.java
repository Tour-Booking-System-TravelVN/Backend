package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, String> {
    @Query("SELECT t FROM Tour t WHERE t.tourId = ?1")
    Optional<Tour> findByTourId(String tourId);

    @Query("SELECT t FROM Tour t WHERE t.tourName LIKE %?1% OR t.departurePlace LIKE %?1% OR t.placesToVisit LIKE %?1%")
    List<Tour> findByLocationOrTourNameContaining(String keyword);
}
