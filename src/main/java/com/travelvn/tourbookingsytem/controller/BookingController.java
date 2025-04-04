package com.travelvn.tourbookingsytem.controller;


import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable String id, @Valid @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending-cancel")
    public ResponseEntity<List<Booking>> getPendingCancelBookings() {
        return ResponseEntity.ok(bookingService.getPendingCancelBookings());
    }

    @PostMapping("/{id}/approve-cancel")
    public ResponseEntity<Booking> approveCancelRequest(@PathVariable String id) {
        Booking updatedBooking = bookingService.approveCancelRequest(id);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/reject-cancel")
    public ResponseEntity<Booking> rejectCancelRequest(@PathVariable String id) {
        Booking updatedBooking = bookingService.rejectCancelRequest(id);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        }
        return ResponseEntity.notFound().build();
    }
}