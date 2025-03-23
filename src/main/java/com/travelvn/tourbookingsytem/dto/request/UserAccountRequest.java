package com.travelvn.tourbookingsytem.dto.request;

import com.travelvn.tourbookingsytem.exception.ErrorCode;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAccountRequest {
    @Size(min = 10, max = 40, message = "USERNAME_INVALID")
    private String username;

    @Size(min = 6, max = 20, message = "PASSWORD_INVALID")
    private String password;

    private CustomerRequest c;

    private AdministratorRequest administrator;

    private TourGuideRequest tourGuide;

    private TourOperatorRequest tourOperator;

    private String status;

    private String email;
}
