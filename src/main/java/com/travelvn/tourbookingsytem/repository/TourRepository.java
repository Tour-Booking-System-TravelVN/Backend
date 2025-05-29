package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.dto.response.peace.TourCalendarResponse;
import com.travelvn.tourbookingsytem.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, String> {
    @Query("SELECT t FROM Tour t WHERE t.tourId = ?1")
    Optional<Tour> findByTourId(String tourId);
    //Lấy ra danh sách tour hot
//    @Query("SELECT tour from Tour tour join tour.tourUnitSet tu WHERE tu.availableCapacity > 0 GROUP BY tour ORDER BY (SUM(tu.maximumCapacity) - SUM(tu.availableCapacity)) desc, tu.adultTourPrice asc, tu.departureDate asc LIMIT 20")
//    List<Tour> hotTours();

    @Query("SELECT new com.travelvn.tourbookingsytem.dto.response.peace.TourCalendarResponse(" +
            "CAST(FUNCTION('MONTH', tu.departureDate) as integer), CAST(FUNCTION('YEAR', tu.departureDate) as integer)) " +
            "FROM TourUnit tu JOIN tu.tour t " +
            "WHERE tu.departureDate >= CURRENT_DATE AND t.tourId = :tourid " +
            "GROUP BY FUNCTION('MONTH', tu.departureDate), FUNCTION('YEAR', tu.departureDate), tu.departureDate " +
            "ORDER BY tu.departureDate asc")
    List<TourCalendarResponse> findDistinctMonthAndYearFromDepartureDate(
            @Param("tourid") String tourid
    );

    @Query("SELECT t FROM Tour t WHERE t.tourName LIKE %?1% OR t.departurePlace LIKE %?1% OR t.placesToVisit LIKE %?1%")
    List<Tour> findByLocationOrTourNameContaining(String keyword);

    @Query("SELECT t FROM Tour t WHERE LOWER(t.tourName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t.departurePlace) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t.placesToVisit) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Tour> searchByNameOrPlace(@Param("keyword") String keyword);


}