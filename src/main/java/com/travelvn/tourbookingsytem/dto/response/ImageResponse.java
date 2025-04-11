package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ImageResponse {
    private Integer id;

    private String imageName;

    private String url;

//    private TourResponse tour;

}