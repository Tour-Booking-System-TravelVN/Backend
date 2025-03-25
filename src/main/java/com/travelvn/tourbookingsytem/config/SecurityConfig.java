package com.travelvn.tourbookingsytem.config;

import com.travelvn.tourbookingsytem.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //Các endpoint được phép gọi khi chưa có token
    private final String[] PUBLIC_ENDPOINTS = {"/login",
            "/auth/token", "/auth/introspect", "/auth/logout","/auth/refresh","/register"};

    private CustomJwtDecoder jwtDecoder;

    /**
     * Lọc xem có cho phép gọi API
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    //Test dùng h2, thực tế dự án sẽ chạy MySQL
    //Bean chỉ được load lên khi dùng MySQL
//    @ConditionalOnProperty(prefix = "spring",
//        value = "datasource.driverClassName",
//        havingValue = "com.mysql.cj.jdbc.Driver")
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        //Xác định filter cho các api
        //Có ví dụ chỉ có khách hàng mới được đăng ký -> thử thôi chưa đăng ký biết ai là khách hàng
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth/refresh").hasRole(Role.CUSTOMER.name())
                        .anyRequest().authenticated());

        //Authentication Provider
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
        );

        //Chống tấn công XSS - Tạm bỏ - Khi lập trình FE nhớ bật lại
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

//    /**
//     * Implement jwtConfigurer, giải mã token
//     *
//     * @return
//     */
//    @Bean
//    public JwtDecoder jwtDecoder(){
//        //Tạo khóa
//        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
//
//        return NimbusJwtDecoder.
//                withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    };

    /**
     * chuyển đổi JWT thành Authentication
     *
     * @return Authentication
     */
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        //Tạo một đối tượng giúp trích xuất quyền từ JWT.
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        //Gán phần prefix
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        //biến JWT thành đối tượng Authentication
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return converter;
    }
}
