package com.travelvn.tourbookingsytem.config;

import com.nimbusds.jose.JOSEException;
import com.travelvn.tourbookingsytem.dto.request.IntrospectRequest;
import com.travelvn.tourbookingsytem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    private AuthenticationService authenticationService;
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
            //Kiểm tra token có còn hợp lệ
            var response = authenticationService.introspect(IntrospectRequest.builder()
                            .token(token)
                            .build());

            //Token không hợp lệ -> ném ngoại lệ code:401
            if(!response.isValid())
                throw new JwtException("invalid token");

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
}
