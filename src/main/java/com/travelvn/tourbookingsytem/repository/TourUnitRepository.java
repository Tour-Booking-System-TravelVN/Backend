package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TourUnitRepository extends JpaRepository<TourUnit, String> {
    @Query("SELECT MAX(t.tourUnitId) FROM TourUnit t WHERE t.tourUnitId LIKE ?1 || '-%'")
    Optional<String> findMaxTourUnitIdByTourIdPrefix(String tourIdPrefix);
}
