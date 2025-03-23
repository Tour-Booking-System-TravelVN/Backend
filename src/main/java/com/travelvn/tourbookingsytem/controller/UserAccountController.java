package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    /**
     * Đăng ký tài khoản (Tạo tài khoản)
     *
     * @param userAccountRequest Tài khoản đăng ký
     * @return API kết quả đăng ký tài khoản
     */
    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody @Valid UserAccountRequest userAccountRequest) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userAccountService.addUserAccount(userAccountRequest));

        return apiResponse;
    }
}
