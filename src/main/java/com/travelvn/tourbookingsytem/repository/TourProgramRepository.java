package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourProgramRepository extends JpaRepository<TourProgram, Integer> {
    List<TourProgram> findAllByTour_TourIdEqualsIgnoreCaseOrderByDay(String tourId);
//    List<TourProgram> findAllByTour_TourIdContainingIgnoreCaseOrderByDay(String tourId);

}
