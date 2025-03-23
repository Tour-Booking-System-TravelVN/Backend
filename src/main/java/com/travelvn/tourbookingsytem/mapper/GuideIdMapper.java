package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.GuideIdRequest;
import com.travelvn.tourbookingsytem.dto.request.GuideRequest;
import com.travelvn.tourbookingsytem.dto.response.GuideIdResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.GuideId;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface GuideIdMapper {
    GuideId toGuideId(GuideIdRequest guideIdRequest);
//    GuideId toGuideId(GuideIdResponse guideIdResponse);

    GuideIdResponse toGuideIdResponse(GuideId guideId);
//    GuideIdRequest toGuideIdRequest(GuideId guideId);
}
