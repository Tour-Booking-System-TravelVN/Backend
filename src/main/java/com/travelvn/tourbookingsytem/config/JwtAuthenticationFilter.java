package com.travelvn.tourbookingsytem.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter implements BearerTokenResolver {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Lấy Cookie từ request
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Arrays.stream(cookies)
                        .filter(cookie -> "token".equals(cookie.getName()))
                        .findFirst()
                        .ifPresent(cookie -> {
                            String jwtToken = cookie.getValue();
                            log.info("Token found in Cookie: {}", jwtToken);
                            // Xử lý xác thực nếu cần (hiện tại chỉ log)
                        });
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error in JwtAuthenticationFilter: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Internal server error in authentication filter\"}");
        }
    }

    public static String extractTokenFromCookie(HttpServletRequest request) {
        log.info("Extracting token from cookies");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            log.info("Cookies found: {}", Arrays.toString(cookies));
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    log.info("Found token cookie: {}", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        log.info("No token cookie found");
        return null;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "token");
        if (cookie != null) {
            log.info("Resolved token from cookie: {}", cookie.getValue());
            return cookie.getValue();
        }
        log.info("No token in cookie, falling back to DefaultBearerTokenResolver");
        return new DefaultBearerTokenResolver().resolve(request);
    }
}