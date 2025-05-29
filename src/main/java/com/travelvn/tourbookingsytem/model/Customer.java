package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(exclude = {"userAccount", "bookingSet", "companionCustomerSet", "tourRatingSet"})
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Integer id;

    @Column(name = "firstname", length = 40)
    private String firstname;

    @Column(name = "lastname", length = 10)
    private String lastname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "citizen_id", length = 12)
    private String citizenId;

    @Column(name = "passport", length = 16)
    private String passport;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "address")
    private String address;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "c")
    private UserAccount userAccount;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "c", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Booking> bookingSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "c", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanionCustomer> companionCustomerSet = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "c", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourRating> tourRatingSet = new HashSet<>();

    public boolean getGender() {
        return gender;
    }
}