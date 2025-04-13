package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRatingRepository extends JpaRepository<TourRating, Integer> {

}
