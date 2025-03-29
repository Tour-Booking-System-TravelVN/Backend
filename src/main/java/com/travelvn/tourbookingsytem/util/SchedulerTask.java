package com.travelvn.tourbookingsytem.util;

import com.travelvn.tourbookingsytem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class SchedulerTask {

    private final AuthenticationService authenticationService;

    /**
     * Chạy tự động mỗi ngày lúc 00:00 (0h)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void runTask() {
        System.out.println("Thread đang chạy lúc: " + new Date());
        authenticationService.clearInvalidToken();
    }
}
