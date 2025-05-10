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
    @Column(name = "tour_id", length = 17)
    private String tourId;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_operator_id")
    private TourOperator tourOperator;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_operator")
    private TourOperator lastUpdatedOperator;

    @Column(name = "tour_name")
    private String tourName;

    @Column(name = "duration", length = 6)
    private String duration;

    @Column(name = "vehicle")
    private String vehicle;

    @Column(name = "target_audience")
    private String targetAudience;

    @Column(name = "departure_place")
    private String departurePlace;

    @Column(name = "places_to_visit")
    private String placesToVisit;

    @Column(name = "cuisine")
    private String cuisine;

    @Column(name = "ideal_time")
    private String idealTime;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "created_time", updatable = false)
    private Instant createdTime;

    @Column(name = "last_updated_time")
    private Instant lastUpdatedTime;

    @Lob
    @Column(name = "inclusions")
    private String inclusions;

    @Lob
    @Column(name = "exclusions")
    private String exclusions;

    @ToString.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<TourProgram> tourProgramSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Image> imageSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
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
    @PrePersist
    protected void onCreate() {
        this.createdTime = Instant.now();
        this.lastUpdatedTime = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedTime = Instant.now();
    }

}