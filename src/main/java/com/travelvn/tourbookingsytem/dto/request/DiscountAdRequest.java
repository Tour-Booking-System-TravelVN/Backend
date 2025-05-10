package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiscountAdRequest {
    private String discountName;
    private BigDecimal discountValue;
    private String discountUnit;
}