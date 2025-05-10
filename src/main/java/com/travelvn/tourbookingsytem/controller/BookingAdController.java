package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.BookingAdRequest;
import com.travelvn.tourbookingsytem.dto.response.BookingAdResponse;
import com.travelvn.tourbookingsytem.service.BookingAdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingAdController {

    @Autowired
    private BookingAdService bookingAdService;

    @GetMapping
    public ResponseEntity<List<BookingAdResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingAdService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingAdResponse> getBookingById(@PathVariable String id) {
        BookingAdResponse bookingAdResponse = bookingAdService.getBookingById(id);
        return bookingAdResponse != null ? ResponseEntity.ok(bookingAdResponse) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BookingAdResponse> createBooking(@Valid @RequestBody BookingAdRequest request) {
        BookingAdResponse bookingAdResponse = bookingAdService.createBooking(request);
        return ResponseEntity.ok(bookingAdResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingAdResponse> updateBooking(@PathVariable String id, @Valid @RequestBody BookingAdRequest bookingAdRequest) {
        BookingAdResponse updatedBooking = bookingAdService.updateBooking(id, bookingAdRequest);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingAdService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending-cancel")
    public ResponseEntity<List<BookingAdResponse>> getPendingCancelBookings() {
        return ResponseEntity.ok(bookingAdService.getPendingCancelBookings());
    }

    @PostMapping("/{id}/approve-cancel")
    public ResponseEntity<BookingAdResponse> approveCancelRequest(@PathVariable String id) {
        BookingAdResponse updatedBooking = bookingAdService.approveCancelRequest(id);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/reject-cancel")
    public ResponseEntity<BookingAdResponse> rejectCancelRequest(@PathVariable String id) {
        BookingAdResponse updatedBooking = bookingAdService.rejectCancelRequest(id);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.badRequest().build();
    }
}