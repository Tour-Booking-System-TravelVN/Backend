package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourUnit;
import jakarta.persistence.LockModeType;
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
}
