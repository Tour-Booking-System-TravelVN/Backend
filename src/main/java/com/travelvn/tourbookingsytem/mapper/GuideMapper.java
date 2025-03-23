package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.GuideRequest;
import com.travelvn.tourbookingsytem.dto.response.GuideResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Guide;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface GuideMapper {
    Guide toGuide(GuideRequest guideRequest);
//    Guide toGuide(GuideResponse guideResponse);

    GuideResponse toGuideResponse(Guide guide);
//    GuideRequest toGuideRequest(Guide guide);
}
