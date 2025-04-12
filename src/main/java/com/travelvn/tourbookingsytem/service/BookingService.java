package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.mapper.BookingMapper;
import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourUnit;
import com.travelvn.tourbookingsytem.repository.BookingRepository;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import com.travelvn.tourbookingsytem.repository.TourUnitRepository;
import com.travelvn.tourbookingsytem.util.BookingIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.SecureRandom;
import java.util.*;


@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TourUnitRepository tourUnitRepository;

    @Autowired
    private BookingIdGenerator bookingIdGenerator;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    @Transactional // Bổ sung: Quản lý giao dịch
    public Booking createBooking(Booking booking) {
        logger.info("Starting to create booking: {}", booking); // Bổ sung: Log chi tiết

        // Kiểm tra Customer
        if (booking.getC() == null || booking.getC().getId() == null) {
            logger.error("Customer must not be null or must have a valid ID");
            throw new IllegalArgumentException("Customer must not be null");
        }
        Optional<Customer> customerOptional = customerRepository.findById(booking.getC().getId());
        if (!customerOptional.isPresent()) {
            logger.error("Customer with ID {} does not exist", booking.getC().getId());
            throw new IllegalArgumentException("Customer with ID " + booking.getC().getId() + " does not exist");
        }
        booking.setC(customerOptional.get());
        logger.debug("Found Customer: {}", customerOptional.get()); // Bổ sung: Log chi tiết

        // Kiểm tra TourUnit
        if (booking.getTourUnit() == null || booking.getTourUnit().getTourUnitId() == null) {
            logger.error("TourUnit must not be null or must have a valid ID");
            throw new IllegalArgumentException("TourUnit must not be null");
        }
        Optional<TourUnit> tourUnitOptional = tourUnitRepository.findById(booking.getTourUnit().getTourUnitId());
        if (!tourUnitOptional.isPresent()) {
            logger.error("TourUnit with ID {} does not exist", booking.getTourUnit().getTourUnitId());
            throw new IllegalArgumentException("TourUnit with ID " + booking.getTourUnit().getTourUnitId() + " does not exist");
        }
        booking.setTourUnit(tourUnitOptional.get());
        logger.debug("Found TourUnit: {}", tourUnitOptional.get()); // Bổ sung: Log chi tiết

        // Đảm bảo bookingId là null để tự sinh
        logger.debug("ma da duocw sinh ra"); // Thay System.out.println bằng logger
        booking.setBookingId(null);

        // Tự động sinh bookingId
        logger.debug("da cho ma banwg o"); // Thay System.out.println bằng logger
        String bookingId = bookingIdGenerator.generateBookingId(); // Sử dụng instance đã inject
        logger.debug("Generated bookingId: {}", bookingId); // Thay System.out.println bằng logger
        logger.debug("gan ma thanh con"); // Thay System.out.println bằng logger
        booking.setBookingId(bookingId);
        logger.debug("luu ma vao csdl"); // Thay System.out.println bằng logger

        // Lưu vào database
        try {
            logger.debug("Saving booking to database: {}", booking); // Bổ sung: Log chi tiết
            Booking savedBooking = bookingRepository.save(booking);
            logger.info("Successfully saved booking with ID: {}", savedBooking.getBookingId()); // Bổ sung: Log chi tiết
            return savedBooking;
        } catch (Exception e) {
            logger.error("Failed to save booking: {}", e.getMessage(), e); // Bổ sung: Xử lý ngoại lệ
            throw new RuntimeException("booking.save.failed: " + e.getMessage(), e);
        }
    }

    public Booking updateBooking(String id, Booking bookingDetails) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if (bookingDetails.getBookingDate() != null) {
                booking.setBookingDate(bookingDetails.getBookingDate());
            }
            if (bookingDetails.getBabyNumber() != null) {
                booking.setBabyNumber(bookingDetails.getBabyNumber());
            }
            if (bookingDetails.getToddlerNumber() != null) {
                booking.setToddlerNumber(bookingDetails.getToddlerNumber());
            }
            if (bookingDetails.getChildNumber() != null) {
                booking.setChildNumber(bookingDetails.getChildNumber());
            }
            if (bookingDetails.getAdultNumber() != null) {
                booking.setAdultNumber(bookingDetails.getAdultNumber());
            }
            if (bookingDetails.getStatus() != null) {
                if (bookingDetails.getStatus().length() != 1) {
                    throw new IllegalArgumentException("Status must be exactly 1 character");
                }
                booking.setStatus(bookingDetails.getStatus());
            }
            booking.setNote(bookingDetails.getNote());
            if (bookingDetails.getTotalAmount() != null) {
                booking.setTotalAmount(bookingDetails.getTotalAmount());
            }
            // Kiểm tra trường quan hệ
            if (bookingDetails.getC() != null && bookingDetails.getC().getId() != null) {
                Optional<Customer> customerOptional = customerRepository.findById(bookingDetails.getC().getId());
                if (!customerOptional.isPresent()) {
                    throw new IllegalArgumentException("Customer with ID " + bookingDetails.getC().getId() + " does not exist");
                }
                booking.setC(customerOptional.get());
            }
            if (bookingDetails.getTourUnit() != null && bookingDetails.getTourUnit().getTourUnitId() != null) {
                Optional<TourUnit> tourUnitOptional = tourUnitRepository.findById(bookingDetails.getTourUnit().getTourUnitId().toString());
                if (!tourUnitOptional.isPresent()) {
                    throw new IllegalArgumentException("TourUnit with ID " + bookingDetails.getTourUnit().getTourUnitId() + " does not exist");
                }
                booking.setTourUnit(tourUnitOptional.get());
            }
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
            if ("P".equals(booking.getStatus())) {
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
            if ("P".equals(booking.getStatus())) {
                booking.setStatus("D");
                return bookingRepository.save(booking);
            }
            throw new IllegalStateException("Booking is not in PENDING_CANCEL status");
        }
        return null;
    }

}