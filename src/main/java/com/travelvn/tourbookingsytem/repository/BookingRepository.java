package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
}
