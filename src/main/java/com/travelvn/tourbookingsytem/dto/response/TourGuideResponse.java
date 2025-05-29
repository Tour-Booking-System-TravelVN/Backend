package com.travelvn.tourbookingsytem.dto.response;

import com.travelvn.tourbookingsytem.model.Guide;
import com.travelvn.tourbookingsytem.model.UserAccount;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourGuideResponse {

    private Integer id;

    private String firstname;

    private String lastname;

    private LocalDate dateOfBirth;

    private Boolean gender = false;

    private String address;

    private String phoneNumber;

    private String citizenId;

    private String hometown;

    private BigDecimal salary;

    private LocalDate startDate;

    private LocalDate endDate;

    private String cardId;

    private String language;

//    private Set<Guide> guideSet = new HashSet<>();
//
//    private UserAccount userAccount;

}