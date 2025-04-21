package com.travelvn.tourbookingsytem.model;

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
@Table(name = "administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrator_id", nullable = false)
    private Integer id;

    @ToString.Exclude
    @OneToOne(mappedBy = "administrator")
    private UserAccount userAccount;

    @ToString.Exclude
    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourRating> tourRatingSet = new HashSet<>();
}