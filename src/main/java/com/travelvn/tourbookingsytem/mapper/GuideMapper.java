package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.GuideAdRequest;
import com.travelvn.tourbookingsytem.dto.response.GuideAdResponse;
import com.travelvn.tourbookingsytem.model.Guide;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface GuideMapper {
    Guide toGuide(GuideAdRequest guideAdRequest);
//    Guide toGuide(GuideResponse guideResponse);

    GuideAdResponse toGuideResponse(Guide guide);
//    GuideRequest toGuideRequest(Guide guide);
}
