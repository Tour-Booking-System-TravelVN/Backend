package com.travelvn.tourbookingsytem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @Column(name = "discount_id", nullable = false)
    private Integer id;

    @Column(name = "discount_name", nullable = false)
    private String discountName;

    @Column(name = "discount_value", nullable = false, precision = 19, scale = 3)
    private BigDecimal discountValue;

    @Column(name = "discount_unit", nullable = false, length = 3)
    private String discountUnit;

    @ToString.Exclude
    @OneToMany(mappedBy = "discount")
    private Set<TourUnit> tourUnitSet = new HashSet<>();
}