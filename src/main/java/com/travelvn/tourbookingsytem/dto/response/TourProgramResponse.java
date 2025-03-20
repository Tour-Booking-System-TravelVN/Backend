package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourProgramResponse {

    private Integer id;

    private TourResponse tour;

    private String locations;

    private Byte day;

    private String mealDescription;

    private String desciption;

}