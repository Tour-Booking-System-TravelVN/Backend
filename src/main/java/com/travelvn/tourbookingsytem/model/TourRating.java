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
@Table(name = "tour_rating")
public class TourRating {
    @Id
    @Column(name = "tour_rating_id", nullable = false)
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "tour_unit_id", nullable = false)
    private TourUnit tourUnit;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "administrator_id", nullable = false)
    private Administrator administrator;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "c_id", nullable = false)
    private Customer c;

    @Column(name = "rating_value", nullable = false)
    private Byte ratingValue;

    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "status", nullable = false)
    private String status;

}