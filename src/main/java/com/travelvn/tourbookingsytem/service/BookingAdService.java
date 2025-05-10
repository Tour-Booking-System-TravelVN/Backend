package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.BookingAdRequest;
import com.travelvn.tourbookingsytem.dto.response.BookingAdResponse;
import com.travelvn.tourbookingsytem.mapper.BookingAdMapper;
import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.repository.BookingRepository;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import com.travelvn.tourbookingsytem.repository.TourUnitRepository;
import com.travelvn.tourbookingsytem.util.BookingIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingAdService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TourUnitRepository tourUnitRepository;

    @Autowired
    private BookingIdGenerator bookingIdGenerator;

    @Autowired
    private BookingAdMapper bookingAdMapper;

    @Transactional(readOnly = true)
    public List<BookingAdResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingAdMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookingAdResponse getBookingById(String id) {
        return bookingRepository.findById(id)
                .map(bookingAdMapper::toResponse)
                .orElse(null);
    }

    @Transactional
    public BookingAdResponse createBooking(BookingAdRequest request) {
        Booking booking = bookingAdMapper.toEntity(request);

        booking.setC(customerRepository.findById(request.getCId())
                .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại")));
        booking.setTourUnit(tourUnitRepository.findById(request.getTourUnitId())
                .orElseThrow(() -> new IllegalArgumentException("TourUnit không tồn tại")));
        booking.setBookingId(bookingIdGenerator.generateBookingId());
        booking.setStatus("P");

        return bookingAdMapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional
    public BookingAdResponse updateBooking(String id, BookingAdRequest request) {
        return bookingRepository.findById(id).map(booking -> {
            bookingAdMapper.updateEntityFromRequest(request, booking);
            if (request.getCId() != null) {
                booking.setC(customerRepository.findById(request.getCId())
                        .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại")));
            }
            if (request.getTourUnitId() != null) {
                booking.setTourUnit(tourUnitRepository.findById(request.getTourUnitId())
                        .orElseThrow(() -> new IllegalArgumentException("TourUnit không tồn tại")));
            }
            return bookingAdMapper.toResponse(bookingRepository.save(booking));
        }).orElse(null);
    }

    @Transactional
    public void deleteBooking(String id) {
        bookingRepository.findById(id).ifPresent(bookingRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<BookingAdResponse> getPendingCancelBookings() {
        return bookingRepository.findPendingCancelBookings().stream()
                .map(bookingAdMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingAdResponse approveCancelRequest(String id) {
        return bookingRepository.findById(id).map(booking -> {
            if (!"W".equals(booking.getStatus())) {
                throw new IllegalStateException("Booking không ở trạng thái W");
            }
            booking.setStatus("D");
            return bookingAdMapper.toResponse(bookingRepository.save(booking));
        }).orElse(null);
    }

    @Transactional
    public BookingAdResponse rejectCancelRequest(String id) {
        return bookingRepository.findById(id).map(booking -> {
            if (!"W".equals(booking.getStatus())) {
                throw new IllegalStateException("Booking không ở trạng thái W");
            }
            booking.setStatus("C");
            return bookingAdMapper.toResponse(bookingRepository.save(booking));
        }).orElse(null);
    }
}