package com.travelvn.tourbookingsytem.util;

import com.travelvn.tourbookingsytem.repository.TourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class TourIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TourIdGenerator.class);
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MAX_ATTEMPTS = 10;

    @Autowired
    private TourRepository tourRepository;

    public String generateTourId(String duration) {
        logger.debug("Starting to generate tourId with duration: {}", duration);

//Kiểm tra duration
        if (duration == null || !duration.matches("\\dN\\dD")) {
            logger.error("Invalid duration format: {}", duration);
            throw new IllegalArgumentException("Invalid duration format: " + duration);
        }

        //Tạo tourId với mẫu TXXX-YYYY-duration
        String tourId;
        int attempts = 0;

        do {
            if (attempts >= MAX_ATTEMPTS) {
                logger.error("Failed to generate a unique tourId after {} attempts", MAX_ATTEMPTS);
                throw new RuntimeException("tour.id.generation.failed");
            }

            // Phần đầu: T
            String prefix = "T";

            // Phần giữa: XXX-YYYY (ngẫu nhiên)
            String firstPart = String.format("%03d", RANDOM.nextInt(1000)); // 000-999
            String secondPart = String.format("%04d", RANDOM.nextInt(10000)); // 0000-9999

            // Kết hợp thành tourId
            tourId = String.format("%s%s-%s-%s", prefix, firstPart, secondPart, duration);
            attempts++;
            logger.debug("Generated tourId attempt {}: {}", attempts, tourId);
        } while (tourRepository.existsById(tourId)); // Kiểm tra trùng lặp

        logger.info("Generated unique tourId: {}", tourId);
        return tourId;
    }
}