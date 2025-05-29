package com.travelvn.tourbookingsytem.dto.response;

import com.travelvn.tourbookingsytem.model.TourUnit;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiscountResponse {
    private Integer id;

    private String discountName;

    private BigDecimal discountValue;

    private String discountUnit;

//    private Set<TourUnitResponse> tourUnitSet;

}