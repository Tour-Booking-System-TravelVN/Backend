package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourGuideAdResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String address;
    private String phoneNumber;
    private String citizenId;
    private String hometown;
    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private String cardId;
    private String language;
}