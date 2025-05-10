package com.travelvn.tourbookingsytem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelvn.tourbookingsytem.validator.DobConstraint;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerAdRequest {
    private String firstname;
    private String lastname;

    @DobConstraint(message = "Ngày sinh phải trước hôm nay", min = 0)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Boolean gender = false;
    private String nationality;
    private String citizenId;
    private String passport;
    private String phoneNumber;
    private String note;
    private String address;
} 
