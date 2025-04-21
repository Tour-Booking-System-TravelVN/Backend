package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "tour")
public class Tour {
    @Id
    @Column(name = "tour_id", nullable = false, length = 17)
    private String tourId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_operator_id", nullable = false)
    @JsonIgnore
    private TourOperator tourOperator;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "last_updated_operator")
    @JsonIgnore
    private TourOperator lastUpdatedOperator;

    @Column(name = "tour_name", nullable = false)
    private String tourName;

    @Column(name = "duration", nullable = false, length = 6)
    private String duration;

    @Column(name = "vehicle", nullable = false)
    private String vehicle;

    @Column(name = "target_audience", nullable = false)
    private String targetAudience;

    @Column(name = "departure_place", nullable = false)
    private String departurePlace;

    @Column(name = "places_to_visit", nullable = false)
    private String placesToVisit;

    @Column(name = "cuisine", nullable = false)
    private String cuisine;

    @Column(name = "ideal_time", nullable = false)
    private String idealTime;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @Column(name = "last_updated_time")
    private Instant lastUpdatedTime;

    @Lob
    @Column(name = "inclusions", nullable = false)
    private String inclusions;

    @Lob
    @Column(name = "exclusions", nullable = false)
    private String exclusions;

    @ToString.Exclude
    @OneToMany(mappedBy = "tour",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private Set<TourProgram> tourProgramSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "tour",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private Set<Image> imageSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private Set<TourUnit> tourUnitSet = new HashSet<>();

    @JsonProperty("categoryId")
    public Integer getCategoryId() {
        return category != null ? category.getId() : null;
    }

    @JsonProperty("tourOperatorId")
    public Integer getTourOperatorId() {
        return tourOperator != null ? tourOperator.getId() : null;
    }

    @JsonProperty("lastUpdatedOperatorId")
    public Integer getLastUpdatedOperatorId() {
        return lastUpdatedOperator != null ? lastUpdatedOperator.getId() : null;
    }
}