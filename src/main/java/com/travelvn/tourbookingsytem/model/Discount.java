package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Integer id;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount_value", precision = 19, scale = 3)
    private BigDecimal discountValue;

    @Column(name = "discount_unit", length = 3)
    private String discountUnit;

    @ToString.Exclude
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourUnit> tourUnitSet = new HashSet<>();
}