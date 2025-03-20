package com.travelvn.tourbookingsytem.dto.response;

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
public class PersonResponse {
    Integer id;
    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    Boolean gender = false;
    String citizenId;
    String phoneNumber;
    String address;
}
