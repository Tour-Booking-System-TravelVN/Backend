package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CatergoryResponse {
    private Integer id;

    private String categoryName;

    private String description;

}