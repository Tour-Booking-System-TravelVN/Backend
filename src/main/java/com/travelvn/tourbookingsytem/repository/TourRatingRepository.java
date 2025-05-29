package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRatingRepository extends JpaRepository<TourRating, Integer> {

    //Lấy 10 bản đánh giá tốt nhất
    List<TourRating> findTop10ByStatusContainingIgnoreCaseAndTourUnit_Tour_TourIdIgnoreCaseOrderByRatingValueDesc(String status, String tourid);

    Optional<TourRating> findTourRatingByC_IdEqualsAndTourUnit_TourUnitIdEqualsIgnoreCase(int id, String tourUnitId);
}
