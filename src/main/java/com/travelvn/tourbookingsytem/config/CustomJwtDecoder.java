package com.travelvn.tourbookingsytem.config;

import com.nimbusds.jose.JOSEException;
import com.travelvn.tourbookingsytem.dto.request.IntrospectAdRequest;
import com.travelvn.tourbookingsytem.dto.request.IntrospectRequest;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Duration;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    private final AuthenticationService authenticationService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    /**
     * Giải mã token
     *
     * @param token token
     * @return jwt
     * @throws JwtException
     */
    @Override
    public Jwt decode(String token) throws JwtException {

        try{
            log.info("DECODING ... ");

            //Kiểm tra token có còn hợp lệ
            var response = authenticationService.introspect(IntrospectRequest.builder()
                            .token(token)
                            .build());

            //Token không hợp lệ -> ném ngoại lệ code:401
            if(!response.isValid()){
                // Lấy token từ cookie
                String tokenFromCookie = getTokenFromCookie(); // Hàm này sẽ lấy token từ cookie

                if (tokenFromCookie == null) {
                    throw new JwtException("Token not found in cookies");
                }

                log.info("TOKEN {}", tokenFromCookie);

                var response2 = authenticationService.introspect(IntrospectRequest.builder()
                        .token(tokenFromCookie)
                        .build());
                if(!response2.isValid()){
                    HttpServletResponse responseServlet = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                    if (responseServlet != null) {
                        log.info("CÓ VÀO ĐÂY");
                        clearJwtCookie(responseServlet);
                    }
                    log.info("CÓ VÀO ĐÂY 3");
                    throw new JwtException("invalid token");
                }
            }
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        //Giải mã
        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }

    /**
     * Xóa token cookie không hợp lệ
     * @param response
     */
    private void clearJwtCookie(HttpServletResponse response) {
        // Tạo HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true) //Dùng http
                .sameSite("None")
                .path("/")
                .maxAge(0) // Xóa cookie
                .domain("")
                .build();

        response.addHeader("Set-Cookie", cookie.toString()); // Cần HttpServletResponse để xóa cookie
    }

    /**
     * Lấy token từ cookie
     *
     * @return token
     */
    private String getTokenFromCookie() {
        // Giả sử request được truyền vào từ controller hoặc filter
        // Tìm cookie có tên "token"
        String token = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
