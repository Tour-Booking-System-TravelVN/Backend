package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CatergoryAdRequest {
    private Integer id;

    private String categoryName;

    private String description;

}