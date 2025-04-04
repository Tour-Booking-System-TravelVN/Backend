package com.travelvn.tourbookingsytem.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
@Table(name = "customer")
public class    Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id", nullable = false)
    private Integer id;

    @Column(name = "firstname", nullable = false, length = 40)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 10)
    private String lastname;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

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
    @OneToOne(mappedBy = "c")
    private UserAccount userAccount;

    @ToString.Exclude
    @OneToMany(mappedBy = "c")
    private Set<Booking> bookingSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "c")
    private Set<CompanionCustomer> companionCustomerSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "c")
    private Set<TourRating> tourRatingSet = new HashSet<>();
}