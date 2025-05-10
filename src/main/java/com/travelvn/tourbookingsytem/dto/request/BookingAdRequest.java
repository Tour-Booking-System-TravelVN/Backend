package com.travelvn.tourbookingsytem.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookingAdRequest {
    @Size(max = 10)
    private String bookingId;

    @NotNull
    private Integer cId;

    @NotBlank
    @Size(max = 40)
    private String tourUnitId;

    private Instant bookingDate;

    @Min(0)
    private Byte babyNumber;

    @Min(0)
    private Byte toddlerNumber;

    @Min(0)
    private Byte childNumber;

    @Min(0)
    private Byte adultNumber;

    @NotBlank
    @Size(max = 1)
    private String status;

    private String note;

    private String paymentId;

    @PositiveOrZero
    private BigDecimal totalAmount;
}