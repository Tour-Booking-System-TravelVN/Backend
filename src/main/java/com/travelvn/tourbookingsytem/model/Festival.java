package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "festival")
public class Festival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "festival_id")
    private Integer id;

    @Column(name = "festival_name")
    private String festivalName;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "display_status")
    private Boolean displayStatus;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourUnit> tourUnitSet = new HashSet<>();
}