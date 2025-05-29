package com.travelvn.tourbookingsytem.dto.response;

import com.travelvn.tourbookingsytem.dto.response.lite.CustomerResponseLite;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAccountResponse {
    private String username;

//    private String password;

    private CustomerResponseLite c;

    private AdministratorResponse administrator;

    private TourGuideResponse tourGuide;

    private TourOperatorResponse tourOperator;

    private String status;

    private String email;
}
