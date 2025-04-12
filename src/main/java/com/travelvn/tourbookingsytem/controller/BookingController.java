package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourUnit;
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
//laasy all danh sach bôkings(chạy)
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
// lấy theo id(chạy)
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String id) {
        Booking booking = bookingService.getBookingById(id);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }
// tọa 1 moiws(loxioxx)
@PostMapping
public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request) {
    // Ánh xạ thủ công từ BookingRequest sang Booking
    // Gán Customer và TourUnit (chỉ set ID, service sẽ xử lý tìm entity)
    Booking booking = new Booking();
    Customer customer = new Customer();
    TourUnit tourUnit = new TourUnit();

    customer.setId(request.getCId());
    booking.setC(customer);
    tourUnit.setTourUnitId(request.getTourUnitId());
    booking.setTourUnit(tourUnit);
    System.out.println(request.getTourUnitId());
    System.out.println(request.getCId());
    System.out.println("Da cahy");

    booking.setBookingDate(request.getBookingDate());
    booking.setBabyNumber(request.getBabyNumber());
    booking.setToddlerNumber(request.getToddlerNumber());
    booking.setChildNumber(request.getChildNumber());
    booking.setAdultNumber(request.getAdultNumber());
    booking.setNote(request.getNote());
    booking.setPayment_id(request.getPayment_id());
    booking.setTotalAmount(request.getTotalAmount());
    booking.setStatus("P"); // Gán mặc định status là "P" (pending)



    // Gọi service để lưu
    return ResponseEntity.ok(bookingService.createBooking(booking));
}
// Cập nhật theo id(chạy)
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable String id, @Valid @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.notFound().build();
    }
// xóa theo id (chạy)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
// hủy booking (chưaduwxl chay)
    @GetMapping("/pending-cancel")
    public ResponseEntity<List<Booking>> getPendingCancelBookings() {
        return ResponseEntity.ok(bookingService.getPendingCancelBookings());
    }
// cấp nật hủy theo id(chưa dl)
    @PostMapping("/{bookingId}/approve-cancel")
    public ResponseEntity<Booking> approveCancelRequest(@PathVariable String bookingId) {
        Booking updatedBooking = bookingService.approveCancelRequest(bookingId);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.badRequest().build();
    }
// khonog hủy theo id(chưa dl)
    @PostMapping("/{bookingId}/reject-cancel")
    public ResponseEntity<Booking> rejectCancelRequest(@PathVariable String bookingId) {
        Booking updatedBooking = bookingService.rejectCancelRequest(bookingId);
        return updatedBooking != null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.badRequest().build();
    }
}