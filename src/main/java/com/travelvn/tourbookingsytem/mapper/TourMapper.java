package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourAdResponse;
import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourRequest;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Image;
import com.travelvn.tourbookingsytem.model.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.stream.Collectors;


@Named("TourMapper")
@Mapper(componentModel = "spring", uses = {ImageMapper.class, CategoryMapper.class})
public interface TourMapper {
    Tour toTour(TourRequest tourRequest);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    @Mapping(target = "imageMap", ignore = true) // Bỏ qua imageMap vì được xử lý riêng
    Tour toEntity(TourAdRequest tourAdRequest);

    @Mapping(target = "imageUrls", expression = "java(tour.getImageMap().values().stream().collect(java.util.stream.Collectors.toList()))")

    TourAdResponse toResponse(Tour tour);

    @Mapping(target = "tourId", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    @Mapping(target = "imageMap", ignore = true) // Bỏ qua imageMap
    void updateEntityFromRequest(TourAdRequest tourAdRequest, @MappingTarget Tour tour);

    @Named("toTourResponseByFound")
    @Mappings({
            @Mapping(target = "tourOperator", ignore = true),
            @Mapping(target = "lastUpdatedOperator", ignore = true),
            @Mapping(target = "tourProgramSet", ignore = true),
            @Mapping(target = "firstImageUrl", ignore = true) // Bỏ qua ánh xạ trực tiếp
    })
    TourResponse toTourResponseByFound(Tour tour);

    default void setFirstImageUrl(Tour tour, @MappingTarget TourResponse tourResponse) {
        // Ưu tiên imageMap (tích hợp Cloudinary)
        if (tour.getImageMap() != null && !tour.getImageMap().isEmpty()) {
            tourResponse.setFirstImageUrl(tour.getImageMap().values().iterator().next());
        }
        // Hồi quy về imageSet (logic cũ)
        else if (tour.getImageSet() != null && !tour.getImageSet().isEmpty()) {
            Image firstImage = tour.getImageSet().iterator().next();
            tourResponse.setFirstImageUrl(firstImage.getUrl());
        }
        // Mặc định nếu không có ảnh
        else {
            tourResponse.setFirstImageUrl("https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/04/anh-ha-noi.jpg");
        }
    }
}