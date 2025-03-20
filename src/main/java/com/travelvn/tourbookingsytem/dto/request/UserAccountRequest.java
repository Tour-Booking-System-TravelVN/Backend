package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAccountRequest {
    private String username;

    private String password;

    private CustomerRequest c;

    private AdministratorRequest administrator;

    private TourGuideRequest tourGuide;

    private TourOperatorRequest tourOperator;

    private String status;

    private String email;
}
