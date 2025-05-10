package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String nationality;
    private String citizenId;
    private String passport;
    private String phoneNumber;
    private String note;
    private String address;
}