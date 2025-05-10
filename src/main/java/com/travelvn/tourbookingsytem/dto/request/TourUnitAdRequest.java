package com.travelvn.tourbookingsytem.dto.request;

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
public class TourUnitAdRequest {
    private String tourUnitId;
    private Integer festivalId;
    private String tourId;
    private Integer discountId;
    private Integer tourOperatorId;
    private Integer lastUpdatedOperatorId;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private BigDecimal adultTourPrice;
    private BigDecimal childTourPrice;
    private BigDecimal toddlerTourPrice;
    private BigDecimal babyTourPrice;
    private BigDecimal adultTourCost;
    private BigDecimal childTourCost;
    private BigDecimal toddlerTourCost;
    private BigDecimal babyTourCost;
    private BigDecimal privateRoomPrice;
    private Instant createdTime;
    private Instant lastUpdatedTime;
    private Short maximumCapacity;
    private Short availableCapacity;
    private BigDecimal totalAdditionalCost;
}