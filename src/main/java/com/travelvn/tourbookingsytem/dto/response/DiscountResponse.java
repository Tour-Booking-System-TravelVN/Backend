package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.math.BigDecimal;

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
}