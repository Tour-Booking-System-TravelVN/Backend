package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT b FROM Booking b WHERE b.status = 'PENDING_CANCEL'")
    List<Booking> findPendingCancelBookings();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'D'")
    Long countCompletedBookings();

    @Query("SELECT COUNT(DISTINCT b.c) FROM Booking b WHERE b.status = 'D'")
    Long countUniqueCustomers();

    @Query("SELECT SUM(b.totalAmount) FROM Booking b WHERE b.status = 'D'")
    Double calculateTotalRevenue();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'C'")
    Long countCancelledBookings();
}
