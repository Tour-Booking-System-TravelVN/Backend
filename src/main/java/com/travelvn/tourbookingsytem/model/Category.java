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
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_id", nullable = false)
    private Integer id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private Set<Tour> tourSet = new HashSet<>();
}