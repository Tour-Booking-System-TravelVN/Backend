package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, String> {
    //Lấy ra danh sách tour hot
//    @Query("SELECT tour from Tour tour join tour.tourUnitSet tu WHERE tu.availableCapacity > 0 GROUP BY tour ORDER BY (SUM(tu.maximumCapacity) - SUM(tu.availableCapacity)) desc, tu.adultTourPrice asc, tu.departureDate asc LIMIT 20")
//    List<Tour> hotTours();
}
