package com.travelvn.tourbookingsytem.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //  Cookie từ request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies)
                    .filter(cookie -> "token".equals(cookie.getName()))  // Tìm cookie tên "token"
                    .findFirst()
                    .ifPresent(cookie -> {
                        String jwtToken = cookie.getValue();
                        System.out.println("Token found in Cookie: " + jwtToken);

                        // Xử lý xác thực (Ví dụ: kiểm tra token hợp lệ, load user từ database)
                    });
        }

        filterChain.doFilter(request, response);
    }

    public static String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
