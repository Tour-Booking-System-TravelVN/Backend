package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(String id, Booking bookingDetails) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setC(bookingDetails.getC());
            booking.setBookingDate(bookingDetails.getBookingDate());
            booking.setBabyNumber(bookingDetails.getBabyNumber());
            booking.setToddlerNumber(bookingDetails.getToddlerNumber());
            booking.setChildNumber(bookingDetails.getChildNumber());
            booking.setAdultNumber(bookingDetails.getAdultNumber());
            booking.setStatus(bookingDetails.getStatus());
            booking.setNote(bookingDetails.getNote());
            booking.setTotalAmount(bookingDetails.getTotalAmount());
            return bookingRepository.save(booking);
        }
        return null;
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getPendingCancelBookings() {
        return bookingRepository.findPendingCancelBookings();
    }

    public Booking approveCancelRequest(String bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if ("PENDING_CANCEL".equals(booking.getStatus())) {
                booking.setStatus("C");
                return bookingRepository.save(booking);
            }
            throw new IllegalStateException("Booking is not in PENDING_CANCEL status");
        }
        return null;
    }

    public Booking rejectCancelRequest(String bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if ("PENDING_CANCEL".equals(booking.getStatus())) {
                booking.setStatus("D");
                return bookingRepository.save(booking);
            }
            throw new IllegalStateException("Booking is not in PENDING_CANCEL status");
        }
        return null;
    }
}