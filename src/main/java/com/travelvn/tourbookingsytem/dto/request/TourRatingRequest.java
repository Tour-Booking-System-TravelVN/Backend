package com.travelvn.tourbookingsytem.dto.request;

import com.travelvn.tourbookingsytem.model.Customer;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class TourRatingRequest {

//    private Integer id;

    private TourUnitRequest tourUnit;

    private AdministratorRequest administrator;

    private Customer c;

    @Min(value = 1)
    @Max(value = 10)
    private Byte ratingValue;

    private String comment;

    private String status;

}