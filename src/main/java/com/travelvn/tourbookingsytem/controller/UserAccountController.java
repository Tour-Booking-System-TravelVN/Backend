package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    //Ví dụ phân quyền theo method: Kiểm tra quyền trước
    //@PostAuthorize(...): Kiểm tả quyền sau khi phương thức được gọi chạy xong
    //Ứng dụng postauth.. : cho phép đọc thông tin của mình: "returnObject.username == authentication.name"
//    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody @Valid UserAccountRequest userAccountRequest) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userAccountService.addUserAccount(userAccountRequest));

        return apiResponse;
    }

    /**
     * API lấy thông tin của mình
     *
     * @return API thông tin của mình
     */
    @GetMapping("/myInfo")
    public ApiResponse<UserAccountResponse> getMyInfo() {
        return ApiResponse.<UserAccountResponse>builder()
                .result(userAccountService.getMyInfo())
                .build();
    }
}
