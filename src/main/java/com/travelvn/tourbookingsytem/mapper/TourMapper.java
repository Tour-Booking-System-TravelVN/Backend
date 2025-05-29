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
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.mapstruct.*;


@Named("TourMapper")
@Mapper(componentModel = "spring", uses = {ImageMapper.class, CategoryMapper.class})
public interface TourMapper {
    Tour toTour(TourRequest tourRequest);
//    Tour toTour(TourResponse tourResponse);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    Tour toEntity(TourAdRequest tourAdRequest);

    TourAdResponse toResponse(Tour tour);

    @Mapping(target = "tourId", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    void updateEntityFromRequest(TourAdRequest tourAdRequest, @MappingTarget Tour tour);
//    TourResponse toTourResponse(Tour tour);

    @Named("toTourResponseByFound")
    @Mappings({
            @Mapping(target = "tourOperator", ignore = true),
            @Mapping(target = "lastUpdatedOperator", ignore = true),
            @Mapping(target = "tourProgramSet", ignore = true),
//            @Mapping(target = "imageSet", ignore = true),
            @Mapping(target = "firstImageUrl", ignore = true) // Bỏ qua ánh xạ trực tiếp cho firstImageUrl
    })
//    @Mapping(target = "tourUnitSet", ignore = true)
    TourResponse toTourResponseByFound(Tour tour);

//    @AfterMapping
    default void setFirstImageUrl(Tour tour, @MappingTarget TourResponse tourResponse) {
        if (tour.getImageSet() != null && !tour.getImageSet().isEmpty()) {
            // Lấy ảnh đầu tiên từ Set (không đảm bảo thứ tự, trừ khi Set có thứ tự như LinkedHashSet)
            Image firstImage = tour.getImageSet().iterator().next();
            tourResponse.setFirstImageUrl(firstImage.getUrl()); // Giả sử class Image có method getUrl()
        } else {
            tourResponse.setFirstImageUrl("https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/04/anh-ha-noi.jpg"); // Hoặc một giá trị mặc định nếu cần
        }
//        System.out.println("AfterMapping is running...");
    }


//    TourRequest toTourRequest(Tour tour);
}
