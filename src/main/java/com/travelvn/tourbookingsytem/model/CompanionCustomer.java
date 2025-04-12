package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "companion_customer")
public class CompanionCustomer {
    @Id
    @Column(name = "companion_customer_id", nullable = false)
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore  // Ngăn chặn vòng lặp khi serialize JSON
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "c_id", nullable = false)
    private Customer c;

}