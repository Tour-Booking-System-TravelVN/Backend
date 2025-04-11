package com.travelvn.tourbookingsytem.dto.request.booktour;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckBeforeBookingRequest {
    private String tourUnitId;
    private int totalQuant;
}
