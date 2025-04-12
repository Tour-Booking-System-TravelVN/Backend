package com.travelvn.tourbookingsytem.util;

import com.travelvn.tourbookingsytem.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class BookingIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(BookingIdGenerator.class);
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MAX_ATTEMPTS = 10;

    @Autowired
    private BookingRepository bookingRepository;

    public String generateBookingId() {
        logger.debug("Starting to generate booking ID");
        String newBookingId;
        int attempts = 0;

        do {
            if (attempts >= MAX_ATTEMPTS) {
                logger.error("Failed to generate a unique booking ID after {} attempts", MAX_ATTEMPTS);
                throw new RuntimeException("booking.id.generation.failed");
            }
            newBookingId = generateRandomId();
            attempts++;
            logger.debug("Generated booking ID attempt {}: {}", attempts, newBookingId);
        } while (bookingRepository.existsById(newBookingId));

        logger.info("Generated unique booking ID: {}", newBookingId);
        return newBookingId;
    }

    private String generateRandomId() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}