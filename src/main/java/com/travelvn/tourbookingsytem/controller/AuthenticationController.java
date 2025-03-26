package com.travelvn.tourbookingsytem.controller;

import com.mysql.cj.log.Log;
import com.nimbusds.jose.JOSEException;
import com.travelvn.tourbookingsytem.dto.request.IntrospectRequest;
import com.travelvn.tourbookingsytem.dto.request.LogoutRequest;
import com.travelvn.tourbookingsytem.dto.request.RefreshTokenRequest;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.IntrospectResponse;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.Duration;

import static com.travelvn.tourbookingsytem.config.JwtAuthenticationFilter.extractTokenFromCookie;

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
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody UserAccountRequest userAccountRequest, HttpServletResponse response) {

        // Lấy token được tạo sau khi kiểm tra username & password
        String jwtToken = authenticationService.authenticate(userAccountRequest).getToken();

        // Tạo HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
                .httpOnly(true)   // Chặn truy cập từ JavaScript (chống XSS)
                .secure(true)     // Chỉ gửi qua HTTPS (tắt nếu đang test localhost)
                .sameSite("Strict")  // Chống CSRF (Chỉ gửi request từ cùng domain)
                .path("/")        // Cookie áp dụng cho toàn bộ trang
                .maxAge(Duration.ofDays(7))  // Token có hiệu lực trong 7 ngày
                .build();

        // Set Cookie vào Response Header
        response.addHeader("Set-Cookie", cookie.toString());

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

    /**
     * API đăng xuất
     *
     * Token của người dùng
     * @return
     */
//    @PostMapping("/logout")
//    public ApiResponse<Void> logout(@RequestBody LogoutRequest token, HttpServletResponse response)
//                    throws ParseException, JOSEException {
//        authenticationService.logOut(token);
//
//        ResponseCookie cookie = ResponseCookie.from("token", "")
//                .httpOnly(true)
//                .secure(true)
//                .sameSite("Strict")
//                .path("/")
//                .maxAge(0) // Xóa cookie
//                .build();
//
//        response.addHeader("Set-Cookie", cookie.toString()); // Cần HttpServletResponse để xóa cookie
//        return ApiResponse.<Void>builder().build();
//    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, HttpServletResponse response)
            throws ParseException, JOSEException {
        String token = extractTokenFromCookie(request);

        if(token==null)
            return ApiResponse.<Void>builder().build();

        LogoutRequest logoutRequest = new LogoutRequest(token);
        authenticationService.logOut(logoutRequest);

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0) // Xóa cookie
                .build();

        response.addHeader("Set-Cookie", cookie.toString()); // Cần HttpServletResponse để xóa cookie

        return ApiResponse.<Void>builder().build();
    }

    /**
     * Api refresh token
     * @param request token
     * @return
     * @throws JOSEException
     * @throws ParseException
     */
    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshTokenRequest request)
                    throws JOSEException, ParseException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
