package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryResponse {
    private Integer id;

    private String categoryName;

    private String description;

//    private Set<TourResponse> tourSet = new HashSet<>();
}