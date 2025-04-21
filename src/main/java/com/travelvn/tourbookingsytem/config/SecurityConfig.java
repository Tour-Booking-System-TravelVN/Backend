package com.travelvn.tourbookingsytem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    //Các endpoint được phép gọi khi chưa có token
//    private final String[] PUBLIC_ENDPOINTS = {"/login",
//            "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh","/register"};
//
//    private CustomJwtDecoder jwtDecoder;
//
//    /**
//     * Lọc xem có cho phép gọi API
//     *
//     * @param httpSecurity
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    //Test dùng h2, thực tế dự án sẽ chạy MySQL
//    //Bean chỉ được load lên khi dùng MySQL
////    @ConditionalOnProperty(prefix = "spring",
////        value = "datasource.driverClassName",
////        havingValue = "com.mysql.cj.jdbc.Driver")
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtFilter) throws Exception {
//
//        httpSecurity.cors(cors -> cors.configurationSource(request -> {
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowedOrigins(Arrays.asList(
//                    "http://localhost:5500",
//                    "http://127.0.0.1:5500"
//            ));
//            config.addAllowedHeader("*");
//            config.addAllowedMethod("*");
//
//            //?
////            config.addExposedHeader("Set-Cookie");
//
//            config.setAllowCredentials(true); //Bỏ comment nếu cần thiết
//            return config;
//        }));
//
//        httpSecurity.httpBasic((basic) -> basic.securityContextRepository(new HttpSessionSecurityContextRepository()));
//
//        //Xác định filter cho các api
//        //Có ví dụ chỉ có khách hàng mới được đăng ký -> thử thôi chưa đăng ký biết ai là khách hàng
//        httpSecurity.authorizeHttpRequests(request ->
//                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
////                        .requestMatchers(HttpMethod.POST, "/auth/refresh").hasRole(Role.CUSTOMER.name())
//                        .requestMatchers(HttpMethod.GET, "/myInfo").authenticated()
//                        // Phân quyền cho ADMINISTRATOR
//                        .requestMatchers("/api/user-accounts/**").hasAuthority("ADMINISTRATOR") // Quản lý tài khoản hệ thống
//                        .requestMatchers("/api/employees/**").hasAuthority("ADMINISTRATOR") // Quản lý thông tin nhân viên (bao gồm tour-guides)
//                        .requestMatchers("/api/tour-ratings/**").hasAuthority("ADMINISTRATOR") // Duyệt đánh giá
//                        .requestMatchers("/api/reports/**").hasAuthority("ADMINISTRATOR") // Xem báo cáo thống kê
//                        .requestMatchers("/api/me/**").hasAuthority("ADMINISTRATOR") // Quản lý thông tin tài khoản cá nhân
//                        // Phân quyền chung cho ADMINISTRATOR và TOUROPERATOR
//                        // /api/customers/**
//                        .requestMatchers(HttpMethod.GET, "/api/customers/**").hasAnyAuthority("ADMINISTRATOR", "TOUROPERATOR") // Xem thông tin khách hàng
//                        .requestMatchers(HttpMethod.POST, "/api/customers/**").hasAuthority("TOUROPERATOR") // Tạo khách hàng (chỉ TOUROPERATOR)
//                        .requestMatchers(HttpMethod.PUT, "/api/customers/**").hasAuthority("TOUROPERATOR") // Cập nhật khách hàng (chỉ TOUROPERATOR)
//                        .requestMatchers(HttpMethod.DELETE, "/api/customers/**").hasAuthority("TOUROPERATOR") // Xóa khách hàng (chỉ TOUROPERATOR)
//                        // /api/tours/**
//                        .requestMatchers(HttpMethod.GET, "/api/tours/**").hasAnyAuthority("ADMINISTRATOR", "TOUROPERATOR") // Xem tour
//                        .requestMatchers(HttpMethod.POST, "/api/tours/**").hasAuthority("TOUROPERATOR") // Tạo tour (chỉ TOUROPERATOR)
//                        .requestMatchers(HttpMethod.PUT, "/api/tours/**").hasAuthority("TOUROPERATOR") // Cập nhật tour (chỉ TOUROPERATOR)
//                        .requestMatchers(HttpMethod.DELETE, "/api/tours/**").hasAuthority("TOUROPERATOR") // Xóa tour (chỉ TOUROPERATOR)
//                        // Phân quyền cho TOUROPERATOR
//                        .requestMatchers("/api/festivals/**").hasAuthority("TOUROPERATOR") // Quản lý lễ hội
//                        .requestMatchers("/api/discounts/**").hasAuthority("TOUROPERATOR") // Quản lý chương trình giảm giá
//                        .requestMatchers("/api/tour-operator/me/**").hasAuthority("TOUROPERATOR") // Quản lý thông tin cá nhân
//                        .requestMatchers("/api/tour-cancellations/**").hasAuthority("TOUROPERATOR") // Duyệt yêu cầu hủy tour
//                        .requestMatchers("/api/employees/tour-guides/**").hasAuthority("TOUROPERATOR") // Xem và tìm kiếm hướng dẫn viên du lịch
//                        // Các yêu cầu khác phải đăng nhập
//                        .anyRequest().authenticated());
//
//
//        //Authentication Provider
//        httpSecurity.oauth2ResourceServer(oauth2 ->
//                oauth2.jwt(jwtConfigurer ->
//                        jwtConfigurer.decoder(jwtDecoder)
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                        .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
//        );
//
//        //Chống tấn công XSS - Tạm bỏ - Khi lập trình FE nhớ bật lại
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//
//        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return httpSecurity.build();
//    }
//
////    /**
////     * Implement jwtConfigurer, giải mã token
////     *
////     * @return
////     */
////    @Bean
////    public JwtDecoder jwtDecoder(){
////        //Tạo khóa
////        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
////
////        return NimbusJwtDecoder.
////                withSecretKey(secretKeySpec)
////                .macAlgorithm(MacAlgorithm.HS512)
////                .build();
////    };
//
//    /**
//     * chuyển đổi JWT thành Authentication
//     *
//     * @return Authentication
//     */
//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter(){
//        //Tạo một đối tượng giúp trích xuất quyền từ JWT.
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//
//        //Gán phần prefix
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
////Xoá roll do grantedAuth.. tự động thêm ROLE_ phía trước không hợp trong Role
//
//        //biến JWT thành đối tượng Authentication
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//
//        return converter;
//    }
//
//    @Bean
//    public CorsFilter corsFilter(){
//        CorsConfiguration config = new CorsConfiguration();
//
//        // Chỉ định nguồn gốc (Frontend)
//        config.setAllowedOrigins(Arrays.asList(
//                "http://localhost:5500",
//                "http://127.0.0.1:5500"
//        ));
//
//        // Cho phép tất cả các method (GET, POST, PUT, DELETE, ...)
//        config.addAllowedMethod("*");
//
//        // Cho phép tất cả các header
//        config.addAllowedHeader("*");
//
//        // Cho phép gửi credentials như cookies, Authorization header
////        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//    }
//}
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Các endpoint được phép gọi khi chưa có token
    private final String[] PUBLIC_ENDPOINTS = {"/login", "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh","/register"};

    /**
     * Lọc xem có cho phép gọi API
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors() // 👉 Bắt buộc thêm dòng này
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
                .anyRequest().permitAll();

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // Cho phép mọi domain (có thể thay bằng cụ thể nếu cần)
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
