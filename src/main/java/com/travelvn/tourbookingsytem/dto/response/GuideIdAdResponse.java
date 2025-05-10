package com.travelvn.tourbookingsytem.dto.response;

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
public class GuideIdAdResponse implements java.io.Serializable {
    private static final long serialVersionUID = -6169060593995685383L;

    private String tourUnitId;

    private Integer tourGuideId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GuideIdAdResponse entity = (GuideIdAdResponse) o;
        return Objects.equals(this.tourGuideId, entity.tourGuideId) &&
                Objects.equals(this.tourUnitId, entity.tourUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourGuideId, tourUnitId);
    }

}