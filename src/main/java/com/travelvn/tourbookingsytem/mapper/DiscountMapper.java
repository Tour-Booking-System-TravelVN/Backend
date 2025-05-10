package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.DiscountRequest;
import com.travelvn.tourbookingsytem.dto.response.DiscountResponse;
import com.travelvn.tourbookingsytem.model.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);

    Discount toEntity(DiscountRequest discountRequest);

    DiscountResponse toResponse(Discount discount);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(DiscountRequest discountRequest, @MappingTarget Discount discount);
}