package com.travelvn.tourbookingsytem.dto.response.peace;

import com.travelvn.tourbookingsytem.dto.response.DiscountResponse;
import com.travelvn.tourbookingsytem.dto.response.FestivalResponse;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorResponse;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourUnitCalendarResponse {
    private String tourUnitId;

    private FestivalResponse festival;

    private DiscountResponse discount;

    private LocalDate departureDate;

    private LocalDate returnDate;

    private BigDecimal adultTourPrice;

    private BigDecimal childTourPrice;

    private BigDecimal toddlerTourPrice;

    private BigDecimal babyTourPrice;

    private BigDecimal privateRoomPrice;

    private Short maximumCapacity;

    private Short availableCapacity;
}
