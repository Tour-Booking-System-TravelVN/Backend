package com.travelvn.tourbookingsytem.dto.request;

import com.travelvn.tourbookingsytem.model.Customer;
import jakarta.validation.constraints.Size;
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

    @Size(min = 20, max = 20)
    private String bookingId;

    private CustomerRequest c;

    private TourUnitRequest tourUnit;

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