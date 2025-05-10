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
public class BookingAdResponse {
    private String bookingId;
    private Integer cId;
    private String tourUnitId;
    private Instant bookingDate;
    private Byte babyNumber;
    private Byte toddlerNumber;
    private Byte childNumber;
    private Byte adultNumber;
    private String status;
    private String note;
    private String paymentId;
    private BigDecimal totalAmount;
}