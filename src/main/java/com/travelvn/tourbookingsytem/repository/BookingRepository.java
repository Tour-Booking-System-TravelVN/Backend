package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.enums.BookingStatus;
import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.model.TourUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT COUNT(b) FROM Booking b JOIN b.tourUnit t JOIN b.c c WHERE (c.id = :cid) AND (t.departureDate < :returnDate) AND (t.returnDate > :departureDate) AND (b.status = 'P' OR b.status='O' OR (b.status = 'H' AND b.expiredAt > :now))")
    Long countTimeConflictTourUnitToBook(
            @Param("cid") int cid,
            @Param("departureDate") LocalDate departureDate,
            @Param("returnDate") LocalDate returnDate,
            @Param("now") long now
    );

    Optional<Booking> findByPaymentId(String paymentId); // Tên trường payment_id được chuyển thành camelCase paymentId

    //DEPRECATED
//    @Modifying
//    @Query("DELETE FROM Booking b where b.status = 'H' AND b.expiredAt < :now")
//    Void deleteInvalidBookings(@Param("now")Long now);

    List<Booking> findAllByStatusAndExpiredAtLessThan(String status, Long expiredAt);

    //Đếm số đơn đặt holding không hợp lệ của khách hàng
    @Query("SELECT COUNT(b) FROM Booking b JOIN b.c c WHERE c.id = :cid AND b.status = 'H' AND b.expiredAt < :now")
    Long countInvalidBookingsByCId(
            @Param("cid") int cid,
            @Param("now") long now
    );

    Optional<Booking> findBookingByOrderCode(long orderCode);

    //Lấy các tour của mình đã đi
    @Query("SELECT b FROM Booking b join b.tourUnit tu join b.c c WHERE c.id = :cid AND b.status = 'D' ORDER BY tu.departureDate desc ")
    Page<Booking> getMyToursDone(
            @Param("cid") int cid,
            Pageable pageable
    );

    //Lấy các tour của mình đã đi
    @Query("SELECT b FROM Booking b join b.tourUnit tu join b.c c WHERE c.id = :cid AND (b.status = 'O' OR b.status = 'P' OR b.status = 'W') ORDER BY b.status asc, tu.departureDate desc ")
    Page<Booking> getMyToursOPW(
            @Param("cid") int cid,
            Pageable pageable
    );

    Optional<Booking> findBookingByC_IdEqualsAndTourUnit_TourUnitIdEqualsIgnoreCaseAndStatusEqualsIgnoreCase(int cid, String tourUnitId, String status);

    @Modifying
    @Query("UPDATE Booking b SET b.status = 'O' WHERE b.status = 'P' AND b.tourUnit.departureDate >= CURRENT_DATE")
    int updateBookingPToO();

    @Modifying
    @Query("UPDATE Booking b SET b.status = 'D' WHERE b.status = 'O' AND b.tourUnit.departureDate < CURRENT_DATE")
    int updateBookingOToD();

    @Query("SELECT b.id FROM Booking b")
    List<String> findAllBookingIds();

    // Lấy danh sách booking đang chờ hủy
    @Query("SELECT b FROM Booking b WHERE b.status = 'W'")
    List<Booking> findPendingCancelBookings();

    // Đếm số booking hoàn thành (giả định 'D' là hoàn thành)
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'D'")
    Long countCompletedBookings();

    // Đếm số khách hàng duy nhất trong các booking hoàn thành
    @Query("SELECT COUNT(DISTINCT b.c) FROM Booking b WHERE b.status = 'D'")
    Long countUniqueCustomers();

    // Tính tổng doanh thu từ các booking hoàn thành (trả về 0.0 nếu không có bản ghi)
    @Query("SELECT COALESCE(SUM(b.totalAmount), 0.0) FROM Booking b WHERE b.status = 'D'")
    Double calculateTotalRevenue();

    // Đếm số booking bị hủy
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'C'")
    Long countCancelledBookings();

    @Query("SELECT b FROM Booking b WHERE b.bookingDate >= :startTime AND b.bookingDate <= :endTime")
    List<Booking> findBookingsByTimeRange(Instant startTime, Instant endTime);

    // Sửa lại tên thuộc tính: thay createdTime => bookingDate
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status AND b.bookingDate >= :startTime AND b.bookingDate <= :endTime")
    Long countBookingsByStatusAndTime(String status, Instant startTime, Instant endTime);

    @Query("SELECT t.tourId, COUNT(b), SUM(b.totalAmount) FROM Booking b JOIN b.tourUnit tu JOIN tu.tour t " +
            "WHERE b.bookingDate >= :startTime AND b.bookingDate <= :endTime GROUP BY t.tourId")
    List<Object[]> findTopSellingTours(Instant startTime, Instant endTime);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingDate >= :startTime AND b.bookingDate <= :endTime " +
            "AND FUNCTION('HOUR', b.bookingDate) BETWEEN :startHour AND :endHour")
    BigDecimal countBookingsByHourRange(Instant startTime, Instant endTime, int startHour, int endHour);
}
