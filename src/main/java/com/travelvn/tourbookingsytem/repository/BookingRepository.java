package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
