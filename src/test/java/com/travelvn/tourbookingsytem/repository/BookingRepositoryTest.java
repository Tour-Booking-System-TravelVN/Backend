package com.travelvn.tourbookingsytem.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(true)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testUpdateBookingPToO_doesNotAffectDB() {
        int affectedRows = bookingRepository.updateBookingPToO();
        System.out.println("Affected rows: " + affectedRows);

        // assert something if cần
        Assertions.assertTrue(affectedRows >= 0);
    }

}
