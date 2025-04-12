package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query("SELECT b.id FROM Booking b")
    List<String> findAllBookingIds();

    // Lấy danh sách booking đang chờ hủy
    @Query("SELECT b FROM Booking b WHERE b.status = 'PENDING_CANCEL'")
    List<Booking> findPendingCancelBookings();

    // Đếm số booking hoàn thành (giả định 'D' là hoàn thành)
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'D'")
    Long countCompletedBookings();

    // Đếm số khách hàng duy nhất trong các booking hoàn thành
    @Query("SELECT COUNT(DISTINCT b.c) FROM Booking b WHERE b.status = 'D'")
    Long countUniqueCustomers();

    // Tính tổng doanh thu từ các booking hoàn thành
    @Query("SELECT COALESCE(SUM(b.totalAmount), 0.0) FROM Booking b WHERE b.status = 'D'")
    Double calculateTotalRevenue();
    // Sử dụng COALESCE để trả về 0.0 nếu không có bản ghi, thay vì null

    // Đếm số booking bị hủy
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'C'")
    Long countCancelledBookings();

}