package com.travelvn.tourbookingsytem.dto.response.lite;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelvn.tourbookingsytem.dto.response.PersonResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponseLite extends PersonResponse {
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

//    @JsonProperty
//    UserAccountResponseLite userAccount;

//    Set<Booking> bookingSet = new HashSet<>();
//
//    Set<CompanionCustomer> companionCustomerSet = new HashSet<>();
//
//    Set<TourRating> tourRatingSet = new HashSet<>();

}
