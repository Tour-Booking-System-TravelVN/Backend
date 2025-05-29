package com.travelvn.tourbookingsytem.util;

import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class SchedulerTask {

    private final AuthenticationService authenticationService;
    private final BookingService bookingService;

    /**
     * Chạy tự động mỗi ngày lúc 00:00 (0h)
     * giây phút giờ ngày tháng thứ
     */
    @Scheduled(cron = "30 37 22 * * ?")
    public void runTask() {
        System.out.println("Thread đang chạy lúc: " + new Date());
        authenticationService.clearInvalidToken();
        bookingService.deleteInvalidBooking();
//        bookingService.updateBookingStatus();
    }
}
