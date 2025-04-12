package com.travelvn.tourbookingsytem.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BookingRequest {
    @NotNull(message = "Customer ID must not be null")
    @Size(max = 11, message = "ID must not exceed 11 characters")
    @JsonProperty("cId") // Chỉ định rõ ràng tên trường trong JSON
    private Integer cId;

    @NotBlank(message = "TourUnit ID must not be blank")
    @Size(max = 21, message = "TourUnit ID must not exceed 21 characters")
    private String tourUnitId;

    @NotNull(message = "Booking date must not be null")
    private Instant bookingDate;

    @NotNull(message = "Baby number must not be null")
    @Min(value = 0, message = "Baby number must not be negative")
    private Byte babyNumber;

    @NotNull(message = "Toddler number must not be null")
    @Min(value = 0, message = "Toddler number must not be negative")
    private Byte toddlerNumber;

    @NotNull(message = "Child number must not be null")
    @Min(value = 0, message = "Child number must not be negative")
    private Byte childNumber;

    @NotNull(message = "Adult number must not be null")
    @Min(value = 0, message = "Adult number must not be negative")
    private Byte adultNumber;

    @Size(max = 1000, message = "Note must not exceed 1000 characters")
    private String note;

    @Size(max = 50, message = "Payment ID must not exceed 50 characters")
    private String payment_id;

    @NotNull(message = "Total amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

}