package com.travelvn.tourbookingsytem.dto.response.lite;

import com.travelvn.tourbookingsytem.dto.response.AdministratorResponse;
import com.travelvn.tourbookingsytem.dto.response.TourGuideResponse;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAccountResponseLite {
    private String username;

//    private String password;

//    private CustomerResponseLite c;

    private AdministratorResponse administrator;

    private TourGuideResponse tourGuide;

    private TourOperatorResponse tourOperator;

    private String status;

    private String email;
}
