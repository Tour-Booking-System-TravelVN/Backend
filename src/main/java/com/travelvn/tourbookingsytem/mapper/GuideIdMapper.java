package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.GuideIdAdRequest;
import com.travelvn.tourbookingsytem.dto.response.GuideIdAdResponse;
import com.travelvn.tourbookingsytem.model.GuideId;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface GuideIdMapper {
    GuideId toGuideId(GuideIdAdRequest guideIdAdRequest);
//    GuideId toGuideId(GuideIdResponse guideIdResponse);

    GuideIdAdResponse toGuideIdResponse(GuideId guideId);
//    GuideIdRequest toGuideIdRequest(GuideId guideId);
}
