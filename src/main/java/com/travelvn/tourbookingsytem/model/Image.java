package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "image_id", nullable = false)
    private Integer id;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "url", nullable = false)
    private String url;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

}