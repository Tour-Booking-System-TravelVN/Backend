package com.travelvn.tourbookingsytem.dto.request;

import com.travelvn.tourbookingsytem.validator.DobConstraint;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerRequest {
    private Integer id;
    private String firstname;
    private String lastname;

    @DobConstraint(min = 0, message = "INVALID_DOB")
    private LocalDate dateOfBirth;

    private Boolean gender = false;
    private String nationality;
    private String citizenId;
    private String passport;
    private String phoneNumber;
    private String note;
    private String address;
//    private UserAccountRequest userAccount;
}
