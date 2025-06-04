package com.travelvn.tourbookingsytem.config;

import com.travelvn.tourbookingsytem.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //Các endpoint được phép gọi khi chưa có token
    private final String[] PUBLIC_ENDPOINTS = {"/login",
            "/auth/token", "/auth/tokenapp", "/auth/introspect", "/auth/logout", "/auth/refresh", "/register",
            "/tour/foundtourlist", "/tourunit/foundtourlist", "/registerapp",
            "/payment/**"};

    //Các endpoint được phép gọi khi chưa có token với phương thức GET
    private final String[] PUBLIC_GET_ENDPOINTS = {"/tourunit/foundtourlist", "/tourunit/calendar", "/tour/calendar/*", "/festival/carousel", "/rating/tour-detail/*", "/program/tour-detail/*"/*, "/order/**"*/};

    //Các endpoint GET của khách hàng
    private final String[] CUSTOMER_GET_ENDPOINTS = {"/booking/checkbeforebooking", "/booking/*", "/customer/myinfo", /*"/tourunit/mytours",*/ "/booking/mytours", "/rating/rating-tour/check"};

    //Các endpoint POST của khách hàng
    private final String[] CUSTOMER_POST_ENDPOINTS = {/*"booking/booktour"*/"/order/create", "/rating/rating-tour"};

    //Các endpoint PUT của khách hàng
    private final String[] CUSTOMER_PUT_ENDPOINTS = {"/changePwd", "/customer/myinfo/update","/booking/cancel/*"};

    //Các endpoint GET của HDV
    private final String[] GENERAL_GET_ENDPOINTS = {"/user/myinfo"};

    private final CustomJwtDecoder jwtDecoder;

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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtFilter) throws Exception {

        httpSecurity.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList(
                    "http://localhost:5500",
                    "http://127.0.0.1:5500"
            ));
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");

            //?
//            config.addExposedHeader("Set-Cookie");

            config.setAllowCredentials(true); //Bỏ comment nếu cần thiết
            return config;
        }));

        httpSecurity.httpBasic((basic) -> basic.securityContextRepository(new HttpSessionSecurityContextRepository()));

        //Xác định filter cho các api
        //Có ví dụ chỉ có khách hàng mới được đăng ký -> thử thôi chưa đăng ký biết ai là khách hàng
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, CUSTOMER_GET_ENDPOINTS).hasRole(Role.CUSTOMER.name())
                        .requestMatchers(HttpMethod.POST, CUSTOMER_POST_ENDPOINTS).hasRole(Role.CUSTOMER.name())
                        .requestMatchers(HttpMethod.PUT, CUSTOMER_PUT_ENDPOINTS).hasRole(Role.CUSTOMER.name())
                        .requestMatchers(HttpMethod.GET, GENERAL_GET_ENDPOINTS).hasAnyRole(Role.TOURGUIDE.name(), Role.CUSTOMER.name(), Role.ADMINISTRATOR.name(), Role.TOUROPERATOR.name())
//                        .requestMatchers(HttpMethod.POST, "/auth/refresh").hasRole(Role.CUSTOMER.name())
                        .anyRequest().authenticated());

        //Authentication Provider
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2
                        .bearerTokenResolver(jwtFilter)
                        .jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(jwtDecoder)
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
        );

        //Chống tấn công XSS - Tạm bỏ - Khi lập trình FE nhớ bật lại
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        //Tạo một đối tượng giúp trích xuất quyền từ JWT.
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        //Gán phần prefix
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        //biến JWT thành đối tượng Authentication
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return converter;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Chỉ định nguồn gốc (Frontend)
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5500",
                "http://127.0.0.1:5500"
        ));

        // Cho phép tất cả các method (GET, POST, PUT, DELETE, ...)
        config.addAllowedMethod("*");

        // Cho phép tất cả các header
        config.addAllowedHeader("*");

        // Cho phép gửi credentials như cookies, Authorization header
//        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
