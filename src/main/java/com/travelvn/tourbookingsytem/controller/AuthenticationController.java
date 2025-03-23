package com.travelvn.tourbookingsytem.controller;

import com.nimbusds.jose.JOSEException;
import com.travelvn.tourbookingsytem.dto.request.IntrospectRequest;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.IntrospectResponse;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    /**
     * API đăng nhập
     *
     * @param userAccountRequest Tài khoản đăng nhập
     * @return Kết quả đăng nhập có chứa token
     */
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody UserAccountRequest userAccountRequest) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(userAccountRequest))
                .build();
    }

    /**
     * API xác nhận token có hợp lệ
     *
     * @param introspectRequest
     * @return
     */
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {

        /*
        //Lấy nội dung trong authen
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        //getAuthorities() trả về danh sách các quyền
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority: {}", grantedAuthority));
        */

        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(introspectRequest))
                .build();
    }
}
