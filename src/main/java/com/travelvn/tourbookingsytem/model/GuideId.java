package com.travelvn.tourbookingsytem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Embeddable
public class GuideId implements java.io.Serializable {
    private static final long serialVersionUID = -6169060593995685383L;
    @Column(name = "tour_unit_id", nullable = false, length = 24)
    private String tourUnitId;

    @Column(name = "tour_guide_id", nullable = false)
    private Integer tourGuideId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GuideId entity = (GuideId) o;
        return Objects.equals(this.tourGuideId, entity.tourGuideId) &&
                Objects.equals(this.tourUnitId, entity.tourUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourGuideId, tourUnitId);
    }

}