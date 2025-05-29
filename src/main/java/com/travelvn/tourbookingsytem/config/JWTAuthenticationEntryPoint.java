package com.travelvn.tourbookingsytem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelvn.tourbookingsytem.dto.response.ApiAdResponse;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Xử lý lỗi (401)
     *
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        log.info("ME");
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiAdResponse<?> apiAdResponse = ApiAdResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        //Khai báo mapper để chuyển object thành json
        ObjectMapper mapper = new ObjectMapper();

        //Trả về json lỗi
        response.getWriter().write(mapper.writeValueAsString(apiAdResponse));
        response.flushBuffer();
    }
}
