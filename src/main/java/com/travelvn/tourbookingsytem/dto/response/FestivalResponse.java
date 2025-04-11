package com.travelvn.tourbookingsytem.dto.response;

import com.travelvn.tourbookingsytem.model.TourUnit;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FestivalResponse {

    private Integer id;

    private String festivalName;

    private String description;

    private Boolean displayStatus = false;

//    private Set<TourUnitResponse> tourUnitSet;
}