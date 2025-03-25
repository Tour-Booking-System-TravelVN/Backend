package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserAccountServiceTest {
    @Autowired
    UserAccountService userAccountService;

    @MockitoBean
    private UserAccountRepository userAccountRepository;

    private UserAccountRequest userAccountRequest;
    private Boolean rs;
    private UserAccount userAccount;

    @BeforeEach
    void initData(){
        userAccountRequest = UserAccountRequest.builder()
                .username("kdthinh04")
                .email("kdthinh04@gmail.com")
                .password("kdthinh04")
                .status("ON")
                .build();

        rs = true;

        userAccount = UserAccount.builder()
                .username("kdthinh04")
                .email("kdthinh04@gmail.com")
                .password("kdthinh04")
                .status("ON")
                .build();
    }

    @Test
    void addUserAccount_validRequest_success() {
        //GIVEN
        when(userAccountRepository.findById(anyString())).thenReturn(Optional.empty());
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(userAccount);

        //WHEN
        var response = userAccountService.addUserAccount(userAccountRequest);

        //THEN
        Assertions.assertThat(response).isTrue();
    }

    @Test
    void addUserAccount_userExisted_fail() {
        //GIVEN
        when(userAccountRepository.findById(anyString())).thenReturn(Optional.of(userAccount));

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> userAccountService.addUserAccount(userAccountRequest));

        //THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "kdthinh04")
    void getMyInfo_validRequest_success() {
        when(userAccountRepository.findById(anyString())).thenReturn(Optional.of(userAccount));

        var response = userAccountService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("kdthinh04");
    }

    @Test
    @WithMockUser(username = "kdthinh04")
    void getMyInfo_userNotFound_error() {
        when(userAccountRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> userAccountService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}
