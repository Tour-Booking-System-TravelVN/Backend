package com.travelvn.tourbookingsytem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties") //Isolation - test với db ảo
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserAccountService userAccountService;

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

        //Mock service để không chạy vào service
        Mockito.when(userAccountService.addUserAccount(ArgumentMatchers.any()))
                .thenReturn(rs);

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

}
