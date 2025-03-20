package com.travelvn.tourbookingsytem.dto.request;

import com.travelvn.tourbookingsytem.model.Customer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class TourRatingRequest {

    private Integer id;

    private TourUnitRequest tourUnit;

    private AdministratorRequest administrator;

    private Customer c;

    private Byte ratingValue;

    private String comment;

    private String status;

}