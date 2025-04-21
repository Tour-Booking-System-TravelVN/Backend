package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(name = "festival_id", nullable = false)
    private Integer id;

    @Column(name = "festival_name", nullable = false)
    private String festivalName;

    @Lob
    @Column(name = "description")
    private String description;

    @ColumnDefault("b'0'")
    @Column(name = "display_status", nullable = false)
    private Boolean displayStatus = false;

    @ToString.Exclude
    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<TourUnit> tourUnitSet = new HashSet<>();
}