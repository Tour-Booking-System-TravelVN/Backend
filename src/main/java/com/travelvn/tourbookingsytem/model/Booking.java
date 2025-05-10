package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
    @Column(name = "booking_id", length = 10)
    private String bookingId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    private Customer c;

    @JsonProperty("cId")
    @Transient
    public Integer getCId() {
        return c != null ? c.getId() : null;
    }

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_unit_id")
    private TourUnit tourUnit;

    @JsonProperty("tourUnitId")
    @Transient
    public String getTourUnitId() {
        return tourUnit != null ? tourUnit.getTourUnitId() : null;
    }

    @Column(name = "booking_date")
    private Instant bookingDate;

    @Column(name = "baby_number")
    private Byte babyNumber;

    @Column(name = "toddler_number")
    private Byte toddlerNumber;

    @Column(name = "child_number")
    private Byte childNumber;

    @Column(name = "adult_number")
    private Byte adultNumber;
    @Column(name = "status", length = 1)
    private String status;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "payment_id")
    private String payment_id;

    @Column(name = "total_amount", precision = 19, scale = 3)
    private BigDecimal totalAmount;

    @ToString.Exclude
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanionCustomer> companionCustomerSet = new HashSet<>();
}