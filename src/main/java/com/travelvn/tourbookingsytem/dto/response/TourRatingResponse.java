package com.travelvn.tourbookingsytem.dto.response;

import com.travelvn.tourbookingsytem.model.Customer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class TourRatingResponse {

    private Integer id;

    private TourUnitResponse tourUnit;

    private AdministratorResponse administrator;

    private Customer c;

    private Byte ratingValue;

    private String comment;

    private String status;

}