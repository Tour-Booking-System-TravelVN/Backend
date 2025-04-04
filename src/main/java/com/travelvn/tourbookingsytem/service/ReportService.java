package com.travelvn.tourbookingsytem.service;


import com.travelvn.tourbookingsytem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private BookingRepository bookingRepository;

    public Long getCompletedBookings() {
        return bookingRepository.countCompletedBookings();
    }

    public Long getUniqueCustomers() {
        return bookingRepository.countUniqueCustomers();
    }

    public Double getTotalRevenue() {
        Double revenue = bookingRepository.calculateTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    public Long getCancelledBookings() {
        return bookingRepository.countCancelledBookings();
    }
}