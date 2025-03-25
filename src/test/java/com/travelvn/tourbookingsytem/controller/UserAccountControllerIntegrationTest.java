package com.travelvn.tourbookingsytem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserAccountControllerIntegrationTest {
    @Container
    static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Autowired
    private MockMvc mockMvc;

    private UserAccountRequest userAccountRequest;
    private Boolean rs;

    @BeforeEach
    void initData(){
        userAccountRequest = UserAccountRequest.builder()
                .username("kdthinh04")
                .email("kdthinh04@gmail.com")
                .password("kdthinh04")
                .status("ON")
                .build();

        rs = true;
    }

    @Test
    void register_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();

        //Với thời gian
//        objectMapper.registerModule(new JavaTimeModule());

        //Tạo nội dung
        String content = objectMapper.writeValueAsString(userAccountRequest);

        //WHEN, THEN
        //Tạo request
        mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000));

        //THEN
    }

    /*
    @Test
    void register_InValidRequest_fail() throws Exception {
        //GIVEN
        userAccountRequest.setUsername("kdt");
        ObjectMapper objectMapper = new ObjectMapper();

        //Với thời gian
//        objectMapper.registerModule(new JavaTimeModule());

        //Tạo nội dung
        String content = objectMapper.writeValueAsString(userAccountRequest);

        //Mock service để không chạy vào service
        //Ở đây thì lỗi sẽ ném ra ở DTO chưa xuống service
//        Mockito.when(userAccountService.addUserAccount(ArgumentMatchers.any()))
//                .thenReturn(rs);

        //WHEN, THEN
        //Tạo request
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Tên người dùng phải có độ dài từ 8 đến 40 ký tự."));

        //THEN
    }
    */
}
