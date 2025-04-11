package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.ImageRequest;
import com.travelvn.tourbookingsytem.dto.response.ImageResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//@Component
@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageRequest imageRequest);
//    Image toImage(ImageResponse imageResponse);

//    ImageRequest toImageRequest(Image image);
//    @Mapping(target = "tour", ignore = true)
    ImageResponse toImageResponse(Image image);
}
