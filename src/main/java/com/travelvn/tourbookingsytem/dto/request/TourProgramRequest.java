package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourProgramRequest {

    private Integer id;

    private TourRequest tour;

    private String locations;

    private Byte day;

    private String mealDescription;

    private String desciption;

}