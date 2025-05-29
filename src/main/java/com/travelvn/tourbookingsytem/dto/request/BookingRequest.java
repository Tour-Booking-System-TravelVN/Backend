package com.travelvn.tourbookingsytem.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookingRequest {

    @Size(min = 20, max = 20, message = "INVALID_BOOKING_ID")
    private String bookingId;

    private CustomerRequest c;

//    private TourUnitRequest tourUnit;
    private String tourUnitId;

    private Instant bookingDate;

    private Byte babyNumber;

    private Byte toddlerNumber;

    private Byte childNumber;

    private Byte adultNumber;

//    private String status;

    private String note;

    private String payment_id;

    private BigDecimal totalAmount;

    private Byte privateRoomNumber;

    private Long orderCode;

    private Long expiredAt;

    @JsonProperty("companions")
    private List<CompanionCustomerRequest> companions;
}