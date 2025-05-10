package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.UserAccountAdRequest;
import com.travelvn.tourbookingsytem.dto.response.UserAccountAdResponse;
import com.travelvn.tourbookingsytem.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserAccountAdMapper {
    UserAccountAdMapper INSTANCE = Mappers.getMapper(UserAccountAdMapper.class);

    @Mapping(target = "c", ignore = true)
    @Mapping(target = "administrator", ignore = true)
    @Mapping(target = "tourGuide", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    UserAccount toEntity(UserAccountAdRequest userAccountAdRequest);

    @Mapping(source = "c.id", target = "customerId")
    @Mapping(source = "administrator.id", target = "administratorId")
    @Mapping(source = "tourGuide.id", target = "tourGuideId")
    @Mapping(source = "tourOperator.id", target = "tourOperatorId")
    UserAccountAdResponse toResponse(UserAccount userAccount);

    @Mapping(target = "c", ignore = true)
    @Mapping(target = "administrator", ignore = true)
    @Mapping(target = "tourGuide", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "username", ignore = true)
    void updateEntityFromRequest(UserAccountAdRequest userAccountAdRequest, @MappingTarget UserAccount userAccount);
}