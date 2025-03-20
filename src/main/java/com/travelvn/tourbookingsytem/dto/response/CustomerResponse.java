package com.travelvn.tourbookingsytem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse extends PersonResponse{
//    Integer id;
//    String firstname;
//    String lastname;
//    LocalDate dateOfBirth;
//    Boolean gender = false;
    String nationality;
//    String citizenId;
    String passport;
//    String phoneNumber;
    String note;
//    String address;

    @JsonProperty
    UserAccountResponse userAccount;
}
