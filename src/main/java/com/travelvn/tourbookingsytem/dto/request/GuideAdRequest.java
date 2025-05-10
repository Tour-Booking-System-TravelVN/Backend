package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GuideAdRequest {

    private GuideIdAdRequest id;

//    @ToString.Exclude
//    @MapsId("tourUnitId")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tour_unit_id", nullable = false)
//    private TourUnit tourUnit;

    private TourGuideAdRequest tourGuide;

    private TourOperatorAdRequest tourOperator;

    private Instant updatedAt;

}