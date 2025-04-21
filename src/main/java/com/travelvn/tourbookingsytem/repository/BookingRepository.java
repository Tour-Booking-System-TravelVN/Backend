package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

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
