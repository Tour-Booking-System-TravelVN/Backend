package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "booking_id", nullable = false, length = 10)
    @NotBlank(message = "Booking ID must not be blank")
    @Size(max = 10, message = "Booking ID must not exceed 10 characters")
    private String bookingId;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "c_id", nullable = false)
    @NotNull(message = "Customer must not be null")
    private Customer c;

    @JsonProperty("cId")
    @Transient
    public Integer getCId() {
        return c != null ? c.getId() : null;
    }

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_unit_id", nullable = false)
    @NotNull(message = "TourUnit must not be null")
    private TourUnit tourUnit;

    @JsonProperty("tourUnitId")
    @Transient
    public String getTourUnitId() {
        return tourUnit != null ? tourUnit.getTourUnitId() : null;
    }

    @Column(name = "booking_date", nullable = false)
    @NotNull(message = "Booking date must not be null")
    private Instant bookingDate;

    @Column(name = "baby_number", nullable = false)
    @NotNull(message = "Baby number must not be null")
    @Min(value = 0, message = "Baby number must not be negative")
    private Byte babyNumber;

    @Column(name = "toddler_number", nullable = false)
    @NotNull(message = "Toddler number must not be null")
    @Min(value = 0, message = "Toddler number must not be negative")
    private Byte toddlerNumber;

    @Column(name = "child_number", nullable = false)
    @NotNull(message = "Child number must not be null")
    @Min(value = 0, message = "Child number must not be negative")
    private Byte childNumber;

    @Column(name = "adult_number", nullable = false)
    @NotNull(message = "Adult number must not be null")
    @Min(value = 0, message = "Adult number must not be negative")
    private Byte adultNumber;

    @Column(name = "status", nullable = false, length = 1)
    @NotBlank(message = "Status must not be blank")
    @Size(max = 1, message = "Status must be 1 character")
    private String status;

    @Lob
    @Column(name = "note")
    @Size(max = 1000, message = "Note must not exceed 1000 characters")
    private String note;

    @Column(name = "payment_id")
    @Size(max = 50, message = "Payment ID must not exceed 50 characters")
    private String payment_id;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 3)
    @NotNull(message = "Total amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanionCustomer> companionCustomerSet = new HashSet<>();
}