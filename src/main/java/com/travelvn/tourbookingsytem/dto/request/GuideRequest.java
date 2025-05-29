package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GuideRequest {

    private GuideIdRequest id;

//    @ToString.Exclude
//    @MapsId("tourUnitId")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tour_unit_id", nullable = false)
//    private TourUnit tourUnit;

    private TourGuideRequest tourGuide;

    private TourOperatorRequest tourOperator;

    private Instant updatedAt;

}