package com.travelvn.tourbookingsytem.util;

import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupTask implements ApplicationRunner {

    private final AuthenticationService authenticationService;
    private final BookingService bookingService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        authenticationService.clearInvalidToken();
//        bookingService.deleteInvalidBooking();
//        bookingService.updateBookingStatus();
        System.out.println("Hello World");
    }
}
