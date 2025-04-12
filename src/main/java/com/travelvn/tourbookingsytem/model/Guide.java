package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "guide")
public class Guide {
    @EmbeddedId
    private GuideId id;

    @ToString.Exclude
    @MapsId("tourUnitId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "tour_unit_id", nullable = false)
    private TourUnit tourUnit;

    @ToString.Exclude
    @MapsId("tourGuideId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "tour_guide_id", nullable = false)
    private TourGuide tourGuide;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "tour_operator_id", nullable = false)
    private TourOperator tourOperator;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}