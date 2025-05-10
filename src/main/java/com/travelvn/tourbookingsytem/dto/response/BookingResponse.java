package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookingResponse {
    private String bookingId;

    private CustomerAdResponse c;

    private TourUnitAdResponse tourUnit;

    private Instant bookingDate;

    private Byte babyNumber;

    private Byte toddlerNumber;

    private Byte childNumber;

    private Byte adultNumber;

    private String status;

    private String note;

    private String payment_id;

    private BigDecimal totalAmount;

}