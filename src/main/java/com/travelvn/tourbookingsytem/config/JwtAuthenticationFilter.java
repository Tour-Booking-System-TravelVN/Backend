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

    private final DefaultBearerTokenResolver defaultBearerTokenResolver = new DefaultBearerTokenResolver();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = resolveToken(request); // Lấy token từ cả cookie và header

        if (jwtToken != null && !jwtToken.isEmpty()) {
            System.out.println("Token found: " + jwtToken);
            // Xử lý xác thực (Ví dụ: kiểm tra token hợp lệ, load user từ database)
        }

        filterChain.doFilter(request, response);

//          Cookie từ request
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            Arrays.stream(cookies)
//                    .filter(cookie -> "token".equals(cookie.getName()))  // Tìm cookie tên "token"
//                    .findFirst()
//                    .ifPresent(cookie -> {
//                        String jwtTokenCookie = cookie.getValue();
//                        System.out.println("Token found in Cookie: " + jwtTokenCookie);
//
//                        // Xử lý xác thực (Ví dụ: kiểm tra token hợp lệ, load user từ database)
//                    });
//        }
//
//        filterChain.doFilter(request, response);
    }

    /**
     * Lấy cookie từ request
     * @param request
     * @return token hoặc null nếu không tìm thấy
     */
    public static String extractTokenFromCookie(HttpServletRequest request) {
//        log.info("Request: " + request.getCookies());
        Cookie[] cookies = request.getCookies();
//        log.info("??? "+Arrays.toString(cookies));
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Lấy cookie từ request
     * @param request
     * @return
     */
//    @Override
//    public String resolve(HttpServletRequest request) {
//        Cookie cookie = WebUtils.getCookie(request, "token");
//        if (cookie != null)
//            return cookie.getValue();
//        else
//            return new DefaultBearerTokenResolver().resolve(request);
//    }

    /**
     * Lấy token từ cookie hoặc header Authorization.
     * @param request HttpServletRequest
     * @return token hoặc null nếu không tìm thấy
     */
    private String resolveToken(HttpServletRequest request) {
        // Kiểm tra cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "token".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        // Nếu không tìm thấy trong cookie, kiểm tra header Authorization
        return defaultBearerTokenResolver.resolve(request);
    }

    /**
     * Lấy token từ cookie hoặc header Authorization.
     * @param request HttpServletRequest
     * @return token hoặc null nếu không tìm thấy
     */
    @Override
    public String resolve(HttpServletRequest request) {
        return resolveToken(request);
    }
}
