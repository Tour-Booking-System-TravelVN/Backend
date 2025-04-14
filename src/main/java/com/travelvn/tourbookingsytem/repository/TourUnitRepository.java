package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.model.TourUnit;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourUnitRepository extends JpaRepository<TourUnit, String> {

//    @Query(value = """
//            SELECT tu.*
//           FROM tour_unit tu
//           JOIN tour t ON t.tour_id = tu.tour_id
//           JOIN (
//               SELECT t2.tour_id, MIN(tu2.departure_date) AS earliestDate
//               FROM tour t2
//               JOIN tour_unit tu2 ON t2.tour_id = tu2.tour_id
//               JOIN discount d ON d.discount_id = tu2.discount_id
//               WHERE t2.tour_name LIKE CONCAT('%', :keywords, '%')
//                 AND t2.description LIKE CONCAT('%', :keywords, '%')
//                 AND tu2.departure_date >= :startDate
//                 AND (
//                   CASE
//                     WHEN d.discount_unit = '%'
//                       THEN tu2.adult_tour_price * (100 - d.discount_value) / 100
//                     ELSE tu2.adult_tour_price - d.discount_value
//                   END
//                 ) <= :maxPrice
//                 AND (
//                   CASE
//                     WHEN d.discount_unit = '%'
//                       THEN tu2.adult_tour_price * (100 - d.discount_value) / 100
//                     ELSE tu2.adult_tour_price - d.discount_value
//                   END
//                 ) >= :minPrice
//                 AND tu2.available_capacity > 0
//               GROUP BY t2.tour_id
//           ) AS temp ON t.tour_id = temp.tour_id
//           WHERE tu.departure_date = temp.earliestDate
//                       """, nativeQuery = true)
//    List<TourUnit> findEarliestDepartureTourUnitsNative(
//            @Param("keywords") String keywords,
//            @Param("startDate") LocalDate startDate,
//            @Param("minPrice") BigDecimal minPrice,
//            @Param("maxPrice") BigDecimal maxPrice
//    );
//
//    @Query(value = """
//            SELECT tu.*
//           FROM tour_unit tu
//           JOIN tour t ON t.tour_id = tu.tour_id
//           JOIN (
//               SELECT t2.tour_id, MIN(tu2.departure_date) AS earliestDate
//               FROM tour t2
//               JOIN tour_unit tu2 ON t2.tour_id = tu2.tour_id
//               JOIN discount d ON d.discount_id = tu2.discount_id
//               WHERE t2.tour_name LIKE CONCAT('%', :keywords, '%')
//                 AND t2.description LIKE CONCAT('%', :keywords, '%')
//                 AND tu2.departure_date >= :startDate
//                 AND (
//                   CASE
//                     WHEN d.discount_unit = '%'
//                       THEN tu2.adult_tour_price * (100 - d.discount_value) / 100
//                     ELSE tu2.adult_tour_price - d.discount_value
//                   END
//                 ) >= :minPrice
//                 AND tu2.available_capacity > 0
//               GROUP BY t2.tour_id
//           ) AS temp ON t.tour_id = temp.tour_id
//           WHERE tu.departure_date = temp.earliestDate
//                       """, nativeQuery = true)
//    List<TourUnit> findEarliestDepartureTourUnitsNative2(
//            @Param("keywords") String keywords,
//            @Param("startDate") LocalDate startDate,
//            @Param("minPrice") BigDecimal minPrice
//    );

    //Chống request cùng lúc gây lưu dữ liệu lỗi
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM TourUnit t WHERE t.tourUnitId = :id")
    TourUnit findByIdForUpdate(@Param("id") String id);

    //Lấy ra số chỗ bị giữ hợp lệ
    @Query("SELECT COALESCE(SUM(b.adultNumber),0) + COALESCE(SUM(b.childNumber),0) + COALESCE(SUM(b.toddlerNumber),0) + COALESCE(SUM(b.babyNumber),0) FROM Booking b WHERE b.status = 'H' AND b.expiredAt > :now GROUP BY b.status")
    Long countBookingsHolding(@Param("now") Long now);

    //Lấy ra danh sách tour hot
//    @Query("SELECT tourunit from TourUnit tourunit WHERE (tourunit.availableCapacity > 0) AND (tourunit.tour.tourId IN (SELECT tour.tourId from Tour tour join tour.tourUnitSet tu GROUP BY tour ORDER BY (SUM(tu.maximumCapacity) - SUM(tu.availableCapacity)) desc LIMIT 20)) ORDER BY tourunit.adultTourPrice asc, tourunit.departureDate asc")
//    List<TourUnit> hotTours();

    //Lấy ra danh sách tour hot
    @Query("SELECT tourunit from TourUnit tourunit JOIN (SELECT tour.tourId as tid from Tour tour join tour.tourUnitSet tu GROUP BY tour ORDER BY (SUM(tu.maximumCapacity) - SUM(tu.availableCapacity)) desc LIMIT 20) as v2 ON tourunit.tour.tourId = v2.tid WHERE (tourunit.availableCapacity > 0) AND (tourunit.departureDate >= CURRENT_DATE ) ORDER BY tourunit.adultTourPrice asc, tourunit.departureDate asc")
    List<TourUnit> hotTours();

    //Lấy ra danh sách tour hot
//    @Query("SELECT tourunit from TourUnit tourunit " +
////            "JOIN tourunit.tour to " +
////            "JOIN tourunit.discount discount " +
//            "JOIN ( SELECT tour.tourId as tid, MIN( tu.departureDate ) as earliestDate from Tour tour join tour.tourUnitSet tu GROUP BY tour HAVING MIN(tu.departureDate) > 0 LIMIT 20 ) as v2 " +
//            "ON tourunit.tour.tourId = v2.tid WHERE ((tourunit.availableCapacity > 0) AND (tourunit.departureDate >= CURRENT_DATE)) ORDER BY tourunit.adultTourPrice asc, tourunit.departureDate asc")
//    @Query(value = "SELECT tourunit from TourUnit tourunit " +
//            "JOIN " +
//            "(SELECT tour.tourId as tid, MIN(tu.departureDate) as earliestDate from Tour tour join tour.tourUnitSet tu JOIN tu.discount discount " +
//            "WHERE discount.discountUnit = '%' AND tu.departureDate >= CURRENT_DATE AND tu.availableCapacity > 0 " +
//            "GROUP BY tour " +
//            "ORDER BY tourunit.adultTourPrice asc, tourunit.departureDate asc, discount.discountValue desc LIMIT 20) as v2 " +
//            "ON tourunit.tour.tourId = v2.tid " +
//            "WHERE tourunit.departureDate = v2.earliestDate ", nativeQuery = true)
////            "WHERE (tourunit.availableCapacity > 0) AND (tourunit.departureDate >= CURRENT_DATE ) " +
////            "ORDER BY tourunit.adultTourPrice asc, tourunit.departureDate asc")
    @Query(value = """
            SELECT tour_unit.* FROM tour_unit 
                JOIN tour ON tour.tour_id = tour_unit.tour_id 
                JOIN discount ON discount.discount_id = tour_unit.discount_id 
                JOIN ( 
                	SELECT tour.tour_id, MIN(tour_unit.departure_date) AS earliestDate 
                	FROM tour 
                	JOIN tour_unit ON tour.tour_id = tour_unit.tour_id 
                	JOIN discount ON discount.discount_id = tour_unit.discount_id 
                	WHERE (discount.discount_unit = '%') AND (tour_unit.departure_date >= CURDATE()) AND (tour_unit.available_capacity>0) 
                	GROUP BY tour.tour_id 
                ) AS temp 
                ON tour.tour_id = temp.tour_id 
                WHERE tour_unit.departure_date = temp.earliestDate 
                ORDER BY discount.discount_value DESC, tour_unit.adult_tour_price ASC 
                """, nativeQuery = true)
//            "WHERE (tourunit.availableCapacity > 0) AND (tourunit.departureDate >= CURRENT_DATE ) " +
//            "ORDER BY tourunit.adultTourPrice asc, tourunit.departureDate asc")
    List<TourUnit> discountedTours();

    //Lấy ra danh sách tour mới
    @Query("SELECT tourunit from TourUnit tourunit JOIN (SELECT tour.tourId as tid from Tour tour join tour.tourUnitSet tu GROUP BY tour ORDER BY tour.createdTime desc LIMIT 20) as v2 ON tourunit.tour.tourId = v2.tid WHERE (tourunit.availableCapacity > 0) AND (tourunit.departureDate >= CURRENT_DATE ) ORDER BY tourunit.adultTourPrice asc, tourunit.departureDate asc")
    List<TourUnit> newTours();

    //Lấy các tour của mình đã đi
    @Query("SELECT tu FROM TourUnit tu join tu.bookingSet b join b.c c WHERE c.id = :cid AND b.status = 'D' ORDER BY tu.departureDate desc ")
    Page<TourUnit> getMyToursDone(
            @Param("cid") int cid,
            Pageable pageable
    );

    //Lấy các tour của mình đã đi
    @Query("SELECT tu FROM TourUnit tu join tu.bookingSet b join b.c c WHERE c.id = :cid AND (b.status = 'O' OR b.status = 'P' OR b.status = 'W') ORDER BY b.status asc, tu.departureDate desc ")
    Page<TourUnit> getMyToursOPW(
            @Param("cid") int cid,
            Pageable pageable
    );

    @Query("SELECT tu from TourUnit tu join tu.tour t where month(tu.departureDate) = :month AND year(tu.departureDate) = :year and t.tourId = :tourId ")
    List<TourUnit> getTourUnitCalendar(
           @Param("month") int month,
           @Param("year") int year,
           @Param("tourId") String tourId
    );
}
