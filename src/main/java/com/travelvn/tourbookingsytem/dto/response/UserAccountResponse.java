package com.travelvn.tourbookingsytem.dto.response;

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

    private CustomerAdResponse c;

    private AdministratorAdResponse administrator;

    private TourGuideAdResponse tourGuide;

    private TourOperatorAdResponse tourOperator;

    private String status;

    private String email;
}
