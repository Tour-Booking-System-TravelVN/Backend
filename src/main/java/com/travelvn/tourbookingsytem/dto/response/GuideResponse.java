package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GuideResponse {

    private GuideIdResponse id;

//    @ToString.Exclude
//    @MapsId("tourUnitId")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tour_unit_id", nullable = false)
//    private TourUnit tourUnit;

    private TourGuideResponse tourGuide;

    private TourOperatorResponse tourOperator;

    private Instant updatedAt;

}