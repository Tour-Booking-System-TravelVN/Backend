package com.travelvn.tourbookingsytem.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.travelvn.tourbookingsytem.dto.request.IntrospectRequest;
import com.travelvn.tourbookingsytem.dto.request.LogoutRequest;
import com.travelvn.tourbookingsytem.dto.request.RefreshTokenRequest;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.IntrospectResponse;
import com.travelvn.tourbookingsytem.dto.response.PersonResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.enums.Role;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.UserAccountMapper;
import com.travelvn.tourbookingsytem.model.InvalidatedToken;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.InvalidatedTokenRepository;
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
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserAccountRepository userAccountRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserAccountMapper userAccountMapper;
    InvalidatedTokenRepository invalidatedTokenRepository;

    //Không cho vào Constructor
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    //Không cho vào Constructor
    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALIDATION_DURATION;

    //Không cho vào Constructor
    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

//    public AuthenticationService(UserAccountRepository userAccountRepository,
//                                 @Value("${jwt.signerKey}") String signerKey) {
//        this.userAccountRepository = userAccountRepository;
//        this.SIGNER_KEY = signerKey;
//    }

    /**
     * Kiểm tra đăng nhập
     *
     * @param userAccountRequest Tài khoản đăng nhập
     * @return Kết quả đăng nhập - token
     */
    public AuthenticationResponse authenticate(UserAccountRequest userAccountRequest) {
        var user = userAccountRepository.findById(userAccountRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(userAccountRequest.getPassword(), user.getPassword());

        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(user.getStatus().equalsIgnoreCase("LOCK")){
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }

//        UserAccount userAccount = userAccountMapper.toUserAccount(userAccountRequest);
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .fullname(getFullName(user))
                .role(buildScope(user))
                .build();
    }

    /**
     * Lấy fullname
     *
     * @param userAccount Tài khoản người dùng
     * @return Tên đầy đủ của họ
     */
    private String getFullName(UserAccount userAccount) {
        String fullname = "";
//        log.info("Có null không: {}", userAccount.getC()==null);
        if(userAccount.getC()!=null){
            fullname = userAccount.getC().getFirstname() + " " + userAccount.getC().getLastname();
        } else if(userAccount.getTourOperator()!=null){
            fullname = userAccount.getTourOperator().getFirstname() + " " + userAccount.getTourOperator().getLastname();
        } else if(userAccount.getTourGuide()!=null){
            fullname = userAccount.getTourGuide().getFirstname() + " " + userAccount.getTourGuide().getLastname();
        } else if(userAccount.getAdministrator()!=null){
            fullname = "Admin";
        }
        return fullname;
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
                        Instant.now().plus(VALIDATION_DURATION, ChronoUnit.SECONDS /*ChronoUnit.HOURS*/).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
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
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (Exception e){
            isValid = false;
        }

//        log.info("Flag1");

        return IntrospectResponse.builder()
                .valid(isValid)
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

    /**
     * Lấy chữ ký token hợp lệ
     *
     * @param token
     * @param isRefresh Có refresh token không
     * @return
     * @throws JOSEException
     * @throws ParseException
     */
    private SignedJWT verifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {
        //Lẫy chữ ký trong token
        SignedJWT signedJWT = SignedJWT.parse(token);

        //Lấy chữ ký của server
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        //Lấy thời gian hết hạn trong payload
        Date expirationTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        //Xác nhận token
        var verified = signedJWT.verify(verifier);

//        log.info(("Flag2"));

        if(!(verified && expirationTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        //Nếu dùng token đã log out
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    /**
     * Đăng xuất (lưu token vào db)
     *
     * @param request token
     */
    public void logOut(LogoutRequest request)
                throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);
            //Lấy body
            String jit = signToken.getJWTClaimsSet().getJWTID();

            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

//            log.info("DATETIME: {}", expiryTime.toInstant());

            invalidatedTokenRepository.save(InvalidatedToken.builder()
                            .tokenId(jit)
                            .expiryDate(expiryTime.toInstant())
                            .build());
        }catch(AppException e){
            log.info("Token already expired");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Xóa toàn bộ token không hợp lệ trong db
     */
    public void clearInvalidToken(){
        invalidatedTokenRepository.deleteAll();
    }

    /**
     * Refresh token
     *
     * @param request Token cần refresh
     * @return Token mới có hiệu lực
     * @throws ParseException
     * @throws JOSEException
     */
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken(), true);

        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        invalidatedTokenRepository.save(InvalidatedToken.builder()
                .tokenId(jit)
                .expiryDate(expiryTime.toInstant())
                .build());

        var username = signJWT.getJWTClaimsSet().getSubject();

        var user = userAccountRepository.findById(username).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED)
        );

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .fullname(getFullName(user))
                .role(buildScope(user))
                .build();

    }
}
