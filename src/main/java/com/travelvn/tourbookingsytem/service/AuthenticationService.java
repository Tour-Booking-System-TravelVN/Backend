package com.travelvn.tourbookingsytem.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.travelvn.tourbookingsytem.dto.request.IntrospectRequest;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.IntrospectResponse;
import com.travelvn.tourbookingsytem.enums.Role;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.UserAccountMapper;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserAccountRepository userAccountRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserAccountMapper userAccountMapper;

    //Không cho vào Constructor
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

//    public AuthenticationService(UserAccountRepository userAccountRepository,
//                                 @Value("${jwt.signerKey}") String signerKey) {
//        this.userAccountRepository = userAccountRepository;
//        this.SIGNER_KEY = signerKey;
//    }

    /**
     * Kiểm tra đăng nhập
     *
     * @param userAccountRequest Tài khoản đăng nhập
     * @return Kết quả đăng nhập
     */
    public AuthenticationResponse authenticate(UserAccountRequest userAccountRequest) {
        var user = userAccountRepository.findById(userAccountRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(userAccountRequest.getPassword(), user.getPassword());

        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(userAccountMapper.toUserAccount(userAccountRequest));

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Tạo JWT
     *
     * @param user Tên tài khoản người dùng
     * @return Chuỗi
     */
    String generateToken(UserAccount user) {
        //Header Token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Body Token

        //subject: user đăng nhập
        //issuer: ai tạo
        //issueTime: thời gian tạo
        //expỉationTime: thời gian hết hạn
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("Travel VN")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        //Signature - Ở đây dùng khóa bí mật (dùng khóa bất đối xứng cũng được)
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create Token", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Kiểm tra Token đúng không
     *
     * @param introspectRequest Chứa token
     * @return  Kết quả verify
     * @throws JOSEException
     * @throws ParseException
     */
    public IntrospectResponse introspect(IntrospectRequest introspectRequest)
            throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        //Lẫy chữ ký trong token
        SignedJWT signedJWT = SignedJWT.parse(token);

        //Lấy chữ ký của server
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        //Lấy thời gian hết hạn trong payload
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }

    /**
     * Tạo quyền từ tài khoản (chuẩn bị đưa vào token)
     *
     * @param userAccount Tài khoản
     * @return  Quyền
     */
    String buildScope(UserAccount userAccount) {
        if(userAccount.getC()!=null)
            return Role.CUSTOMER.name();
//            return "CUSTOMER";

        if(userAccount.getAdministrator()!=null)
            return Role.ADMINISTRATOR.name();
//            return "ADMINISTRATOR";

        if(userAccount.getTourGuide()!=null)
            return Role.TOURGUIDE.name();
//            return "TOURGUIDE";

        if(userAccount.getTourOperator()!=null)
            return Role.TOUROPERATOR.name();
//            return "TOUROPERATOR";

        return "NULL";
    }
}
