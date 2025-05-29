package com.travelvn.tourbookingsytem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.lite.UserAccountResponseLite;
import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.model.CompanionCustomer;
import com.travelvn.tourbookingsytem.model.TourRating;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    UserAccountResponseLite userAccount;

//    Set<Booking> bookingSet = new HashSet<>();
//
//    Set<CompanionCustomer> companionCustomerSet = new HashSet<>();
//
//    Set<TourRating> tourRatingSet = new HashSet<>();

}
