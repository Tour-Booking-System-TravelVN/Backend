package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tour_unit")
public class TourUnit {
    @Id
    @Column(name = "tour_unit_id", nullable = false, length = 24)
    private String tourUnitId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "festival_id", nullable = false)
    private Festival festival;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_operator_id", nullable = false)
    private TourOperator tourOperator;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_operator")
    private TourOperator lastUpdatedOperator;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "adult_tour_price", nullable = false, precision = 19, scale = 3)
    private BigDecimal adultTourPrice;

    @Column(name = "child_tour_price", nullable = false, precision = 19, scale = 3)
    private BigDecimal childTourPrice;

    @Column(name = "toddler_tour_price", nullable = false, precision = 19, scale = 3)
    private BigDecimal toddlerTourPrice;

    @Column(name = "baby_tour_price", nullable = false, precision = 19, scale = 3)
    private BigDecimal babyTourPrice;

    @Column(name = "adult_tour_cost", nullable = false, precision = 19, scale = 3)
    private BigDecimal adultTourCost;

    @Column(name = "child_tour_cost", nullable = false, precision = 19, scale = 3)
    private BigDecimal childTourCost;

    @Column(name = "toddler_tour_cost", nullable = false, precision = 19, scale = 3)
    private BigDecimal toddlerTourCost;

    @Column(name = "baby_tour_cost", nullable = false, precision = 19, scale = 3)
    private BigDecimal babyTourCost;

    @Column(name = "private_room_price", nullable = false, precision = 19, scale = 3)
    private BigDecimal privateRoomPrice;

    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @Column(name = "last_updated_time")
    private Instant lastUpdatedTime;

    @Column(name = "maximum_capacity", nullable = false)
    private Short maximumCapacity;

    @Column(name = "available_capacity", nullable = false)
    private Short availableCapacity;

    @Column(name = "total_additional_cost", precision = 19, scale = 3)
    private BigDecimal totalAdditionalCost;

    @ToString.Exclude
    @OneToMany(mappedBy = "tourUnit")
    @JsonIgnore
    private Set<Guide> guideSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "tourUnit")
    @JsonIgnore
    private Set<Booking> bookingSet = new HashSet<>();
}