package com.travelvn.tourbookingsytem.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tour_program")
public class TourProgram {
    @Id
    @Column(name = "tour_program_id", nullable = false)
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "locations", nullable = false)
    private String locations;

    @Column(name = "day", nullable = false)
    private Byte day;

    @Column(name = "meal_description", nullable = false, length = 50)
    private String mealDescription;

    @Lob
    @Column(name = "desciption", columnDefinition = "TEXT", nullable = false)
    private String desciption;

}