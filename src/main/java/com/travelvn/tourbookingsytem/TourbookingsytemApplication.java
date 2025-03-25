package com.travelvn.tourbookingsytem;

import com.travelvn.tourbookingsytem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequiredArgsConstructor
public class TourbookingsytemApplication {

	private static AuthenticationService authenticationService;

	public static void main(String[] args) {
		SpringApplication.run(TourbookingsytemApplication.class, args);

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
			// Đoạn mã chạy lúc 0h mỗi ngày
			System.out.println("Thread đang chạy lúc: " + new Date());
			// ... (Thực hiện công việc) ...
			authenticationService.clearInvalidToken();
		};

		// Tính toán thời gian chạy đầu tiên
		long initialDelay = calculateInitialDelay();

		// Lên lịch chạy task mỗi ngày vào lúc 0h
		scheduler.scheduleAtFixedRate(task, initialDelay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS);
	}

	/**
	 * Tính toán thời gian chạy đầu tiên
	 * @return thời gian chạy đầu tiên
	 */
	private static long calculateInitialDelay() {
		Calendar now = Calendar.getInstance();
		Calendar runTime = Calendar.getInstance();

		// Đặt thời gian chạy là 0h ngày mai
		runTime.add(Calendar.DAY_OF_MONTH, 1);
		runTime.set(Calendar.HOUR_OF_DAY, 0);
		runTime.set(Calendar.MINUTE, 0);
		runTime.set(Calendar.SECOND, 0);
		runTime.set(Calendar.MILLISECOND, 0);

		// Tính toán độ trễ ban đầu (thời gian từ bây giờ đến 0h ngày mai)
		return runTime.getTimeInMillis() - now.getTimeInMillis();
	}
}
