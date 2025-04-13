package com.travelvn.tourbookingsytem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelvn.tourbookingsytem.validator.DepartureDateConstraint;
import com.travelvn.tourbookingsytem.validator.DobConstraint;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindTourRequest {
    @JsonProperty("keywords")
    private String keywords;

//    @DobConstraint(min = 0, message = "INVALID_DOB")
    @JsonProperty("departure_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DepartureDateConstraint(message = "INVALID_DEPARTURE_DATE")
    private LocalDate departureDate;

    @JsonProperty("price")
    private String price;

    @JsonProperty("page")
    private int page;

    private int type;
}
