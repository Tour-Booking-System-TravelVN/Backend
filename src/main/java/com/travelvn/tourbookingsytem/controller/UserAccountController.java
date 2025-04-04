package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

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

        // Lấy token được tạo sau khi kiểm tra username & password
        String jwtToken = authenticationService.authenticate(userAccountRequest).getToken();

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

        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(userAccountRequest))
                .build();
    }

    /**
     * API lấy thông tin của mình
     *
     * @return API thông tin của mình
     */
//    @GetMapping("/myInfo")
//    public ApiResponse<UserAccountResponse> getMyInfo() {
//        return ApiResponse.<UserAccountResponse>builder()
//                .result(userAccountService.getMyInfo())
//                .build();
//    }
//Quang anh
@GetMapping
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<List<UserAccount>> getAllUserAccounts() {
    return ResponseEntity.ok(userAccountService.getAllUserAccounts());
}

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserAccount> getUserAccountByUsername(@PathVariable String username) {
        UserAccount userAccount = userAccountService.getUserAccountByUsername(username);
        if (userAccount != null) {
            return ResponseEntity.ok(userAccount);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserAccount> createUserAccount(@Valid @RequestBody UserAccount userAccount) {
        return ResponseEntity.ok(userAccountService.createUserAccount(userAccount));
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserAccount> updateUserAccount(@PathVariable String username, @Valid @RequestBody UserAccount userAccountDetails) {
        UserAccount updatedUserAccount = userAccountService.updateUserAccount(username, userAccountDetails);
        if (updatedUserAccount != null) {
            return ResponseEntity.ok(updatedUserAccount);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable String username) {
        userAccountService.deleteUserAccount(username);
        return ResponseEntity.noContent().build();
    }
}
