package com.travelvn.tourbookingsytem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
@Slf4j
class TourbookingsytemApplicationTests {

	@Test
	void hash() throws NoSuchAlgorithmException {
		String password = "123456";

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		log.info("BCrypt round 1: {}", encoder.encode(password));
		log.info("BCrypt round 2: {}", encoder.encode(password));
	}

}
