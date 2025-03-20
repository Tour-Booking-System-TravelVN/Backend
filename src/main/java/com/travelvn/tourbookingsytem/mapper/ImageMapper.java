package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.ImageRequest;
import com.travelvn.tourbookingsytem.dto.response.ImageResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Image;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageRequest imageRequest);
    Image toImage(ImageResponse imageResponse);

    ImageRequest toImageRequest(Image image);
    ImageResponse toImageResponse(Image image);
}
