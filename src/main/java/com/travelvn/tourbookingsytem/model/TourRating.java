package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_rating_id", nullable = false)
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_unit_id", nullable = false)
    private TourUnit tourUnit;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrator_id", nullable = true)
    private Administrator administrator;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "c_id", nullable = false)
    private Customer c;

    @Column(name = "rating_value", nullable = false)
    private Byte ratingValue;

    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "status", nullable = false)
    private String status;

    @JsonProperty("tourUnitId")
    public String getTourUnitId() {
        return tourUnit != null ? tourUnit.getTourUnitId() : null;
    }

    @JsonProperty("customerId")
    public Integer getCustomerId() {
        return c != null ? c.getId() : null;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public TourUnit getTourUnit() {
        return tourUnit;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Administrator getAdministrator() {
        return administrator;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Customer getC() {
        return c;
    }
}