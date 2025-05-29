package com.travelvn.tourbookingsytem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelvn.tourbookingsytem.dto.request.lite.UserAccountRequestLite;
import com.travelvn.tourbookingsytem.validator.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@CitizenIdConstraint(citizenId = "citizenId", dateOfBirth = "dateOfBirth", gender = "gender", message = "INVALID_CI")
public class CustomerRequest {
//    private Integer id;

    @Size(min = 2, max = 40)
    @FirstNameConstraint(message = "INVALID_FIRSTNAME")
    @JsonProperty("firstname")
    private String firstname;

    @Size(min = 2, max = 10)
    @LastNameConstraint(message = "INVALID_LASTNAME")
    @JsonProperty("lastname")
    private String lastname;

    @DobConstraint(min = 0, message = "INVALID_DOB")
    @JsonProperty("dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    private Boolean gender = false;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("citizenid")
    private String citizenId;

    @Size(min = 6, max = 16)
    private String passport;

    @PhoneNumberConstraint(message = "INVALID_PHONENUMBER")
    @JsonProperty("phonenumber")
    private String phoneNumber;

    @JsonProperty("note")
    private String note;

    @Size(min = 6, max = 255)
    @JsonProperty("address")
    private String address;

    private UserAccountRequestLite userAccount;
}
