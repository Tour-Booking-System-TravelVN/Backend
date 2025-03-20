package com.travelvn.tourbookingsytem.model;

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
    @Column(name = "booking_id", nullable = false, length = 10)
    private String bookingId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "c_id", nullable = false)
    private Customer c;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_unit_id", nullable = false)
    private TourUnit tourUnit;

    @Column(name = "booking_date", nullable = false)
    private Instant bookingDate;

    @Column(name = "baby_number", nullable = false)
    private Byte babyNumber;

    @Column(name = "toddler_number", nullable = false)
    private Byte toddlerNumber;

    @Column(name = "child_number", nullable = false)
    private Byte childNumber;

    @Column(name = "adult_number", nullable = false)
    private Byte adultNumber;

    @Column(name = "status", nullable = false, length = 1)
    private String status;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 3)
    private BigDecimal totalAmount;

    @ToString.Exclude
    @OneToMany(mappedBy = "booking")
    private Set<CompanionCustomer> companionCustomerSet = new HashSet<>();
}