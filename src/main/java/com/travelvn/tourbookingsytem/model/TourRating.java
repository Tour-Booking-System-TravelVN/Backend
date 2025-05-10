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
    @Column(name = "tour_rating_id")
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_unit_id")
    private TourUnit tourUnit;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private Administrator administrator;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    private Customer c;

    @Column(name = "rating_value")
    private Byte ratingValue;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
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