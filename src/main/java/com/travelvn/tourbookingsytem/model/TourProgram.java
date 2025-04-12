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
@Table(name = "tour_program")
public class TourProgram {
    @Id
    @Column(name = "tour_program_id", nullable = false)
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "locations", nullable = false)
    private String locations;

    @Column(name = "day", nullable = false)
    private Byte day;

    @Column(name = "meal_description", nullable = false, length = 20)
    private String mealDescription;

    @Lob
    @Column(name = "desciption", nullable = false)
    private String desciption;

}