package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @JsonProperty("festivalId")
    @Transient
    public Integer getFestivalId() {
        return festival != null ? festival.getId() : null;
    }

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @JsonProperty("tourId")
    @Transient
    public String getTourId() {
        return tour != null ? tour.getTourId() : null;
    }

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @JsonProperty("discountId")
    @Transient
    public Integer getDiscountId() {
        return discount != null ? discount.getId() : null;
    }

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_operator_id")
    private TourOperator tourOperator;

    @JsonProperty("tourOperatorId")
    @Transient
    public Integer getTourOperatorId() {
        return tourOperator != null ? tourOperator.getId() : null;
    }

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_operator")
    private TourOperator lastUpdatedOperator;

    @JsonProperty("lastUpdatedOperatorId")
    @Transient
    public Integer getLastUpdatedOperatorId() {
        return lastUpdatedOperator != null ? lastUpdatedOperator.getId() : null;
    }

    @Column(name = "departure_date")
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
    @JsonIgnore
    @OneToMany(mappedBy = "tourUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Guide> guideSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "tourUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Booking> bookingSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "tourUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourRating> ratingSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "tourUnit")
    private Set<TourRating> tourRatingSet = new HashSet<>();
}