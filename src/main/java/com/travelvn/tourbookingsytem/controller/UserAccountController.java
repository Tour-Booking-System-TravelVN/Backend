package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final AuthenticationService authenticationService;

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
//    @PostMapping("/register")
//    public ApiResponse<Boolean> register(@RequestBody @Valid UserAccountRequest userAccountRequest) {
//        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
//
//        apiResponse.setResult(userAccountService.addUserAccount(userAccountRequest));
//
//        return apiResponse;
//    }

    /**
     * API Đăng ký tài khoản
     *
     * @param userAccountRequest yêu cầu đăng ký
     * @return token
     */
    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@RequestBody @Valid UserAccountRequest userAccountRequest, HttpServletResponse response) {
//        log.info("UserAccountRequest : {}", userAccountRequest);
//        log.info("Before");
        userAccountService.addUserAccount(userAccountRequest);

//        log.info("After");

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(userAccountRequest);
        // Lấy token được tạo sau khi kiểm tra username & password
        String jwtToken = authenticationResponse.getToken();

        // Tạo HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
                .httpOnly(true)   // Chặn truy cập từ JavaScript (chống XSS)
                .secure(true)     // Chỉ gửi qua HTTPS (tắt nếu đang test localhost)
                .sameSite("None")  // Chống CSRF (Chỉ gửi request từ cùng domain)
                .path("/")        // Cookie áp dụng cho toàn bộ trang
                .maxAge(Duration.ofDays(7))  // Token có hiệu lực trong 7 ngày
                .domain("")
                .build();

        // Set Cookie vào Response Header
        response.addHeader("Set-Cookie", cookie.toString());

        //Xóa token đi
        authenticationResponse.setToken("");

        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
    }

    /**
     * API Đăng ký tài khoản app
     *
     * @param userAccountRequest yêu cầu đăng ký
     * @return token
     */
    @PostMapping("/registerapp")
    public ApiResponse<AuthenticationResponse> registerapp(@RequestBody @Valid UserAccountRequest userAccountRequest, HttpServletResponse response) {
        userAccountService.addUserAccount(userAccountRequest);

//        log.info("After");

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(userAccountRequest);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
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
