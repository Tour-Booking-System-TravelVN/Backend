package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.ImageAdRequest;
import com.travelvn.tourbookingsytem.dto.response.ImageAdResponse;
import com.travelvn.tourbookingsytem.model.Image;
import com.travelvn.tourbookingsytem.model.TourOperator;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageAdRequest imageAdRequest);
//    Image toImage(ImageResponse imageResponse);
default TourOperator map(Integer id) {
    if (id == null) {
        return null;
    }
    TourOperator tourOperator = new TourOperator();
    tourOperator.setId(id); // Gán ID cho đối tượng TourOperator
    return tourOperator;
}
//    ImageRequest toImageRequest(Image image);
    ImageAdResponse toImageResponse(Image image);
}
