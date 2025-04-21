package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.ImageRequest;
import com.travelvn.tourbookingsytem.dto.response.ImageResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Image;
import com.travelvn.tourbookingsytem.model.TourOperator;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageRequest imageRequest);
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
    ImageResponse toImageResponse(Image image);
}
