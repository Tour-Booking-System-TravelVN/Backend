package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tour_operator")
public class TourOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_operator_id")
    private Integer id;

    @Column(name = "firstname", length = 40)
    private String firstname;

    @Column(name = "lastname", length = 10)
    private String lastname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "citizen_id", length = 12)
    private String citizenId;

    @Column(name = "hometown")
    private String hometown;

    @Column(name = "salary", precision = 19, scale = 3)
    private BigDecimal salary;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "tourOperator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tour> tourCreatedSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "lastUpdatedOperator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tour> tourUpdatedSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "tourOperator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourUnit> tourUnitCreatedSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "lastUpdatedOperator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tour> tourUnitUpdatedSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "tourOperator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Guide> guideSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "tourOperator", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserAccount userAccount;
}