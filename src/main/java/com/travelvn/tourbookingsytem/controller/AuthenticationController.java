package com.travelvn.tourbookingsytem.controller;

import com.mysql.cj.log.Log;
import com.nimbusds.jose.JOSEException;
import com.travelvn.tourbookingsytem.config.JwtAuthenticationFilter;
import com.travelvn.tourbookingsytem.dto.request.IntrospectRequest;
import com.travelvn.tourbookingsytem.dto.request.LogoutRequest;
import com.travelvn.tourbookingsytem.dto.request.RefreshTokenRequest;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.IntrospectResponse;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.Duration;
import java.util.Collections;

import static com.travelvn.tourbookingsytem.config.JwtAuthenticationFilter.extractTokenFromCookie;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * API đăng nhập
     *
     * @param userAccountRequest Tài khoản đăng nhập
     * @return Kết quả đăng nhập có chứa token
     */
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody UserAccountRequest userAccountRequest, HttpServletResponse response) {

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(userAccountRequest);

        // Lấy token được tạo sau khi kiểm tra username & password
        String jwtToken = authenticationResponse.getToken();

        log.info("JWT token: " + jwtToken);

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

        authenticationResponse.setToken("");//Đã lưu token vào cookie thì không trả token về

        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
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

//    @PreAuthorize("hasAnyRole('CUSTOMER','TOURGUIDE','TOUROPERATOR','ADMINISTRATOR')")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, HttpServletResponse response)
            throws ParseException, JOSEException {

        log.info("Received logout request...");

        // Log toàn bộ headers
        Collections.list(request.getHeaderNames()).forEach(header ->
                log.info("{}: {}", header, request.getHeader(header))
        );

        // Log toàn bộ cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                log.info("Cookie received: {} = {}", cookie.getName(), cookie.getValue());
            }
        } else {
            log.info("No cookies received.");
        }

        extractTokenFromCookie(request);
        String token = jwtAuthenticationFilter.resolve(request);

        log.info("Token trước logout: {}",token);
        if(token==null)
            return ApiResponse.<Void>builder().build();

        log.info("Token không null");

        LogoutRequest logoutRequest = new LogoutRequest(token);
        authenticationService.logOut(logoutRequest);

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true) //Dùng http
                .sameSite("None")
                .path("/")
                .maxAge(0) // Xóa cookie
                .domain("")
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
