package com.travelvn.tourbookingsytem.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAccountRequest {
    @Size(min = 8, max = 40, message = "USERNAME_INVALID")
    @NotNull
    private String username;

    @Size(min = 6, max = 20, message = "PASSWORD_INVALID")
    @NotNull
    private String password;

    private CustomerAdRequest c;

    private AdministratorAdRequest administrator;

    private TourGuideAdRequest tourGuide;

    private TourOperatorAdRequest tourOperator;

    private String status;

    private String email;
}
