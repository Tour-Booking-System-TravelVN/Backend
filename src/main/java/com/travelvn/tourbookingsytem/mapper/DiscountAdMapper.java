package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.DiscountAdRequest;
import com.travelvn.tourbookingsytem.dto.response.DiscountAdResponse;
import com.travelvn.tourbookingsytem.model.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiscountAdMapper {
    DiscountAdMapper INSTANCE = Mappers.getMapper(DiscountAdMapper.class);

    Discount toEntity(DiscountAdRequest discountAdRequest);

    DiscountAdResponse toResponse(Discount discount);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(DiscountAdRequest discountAdRequest, @MappingTarget Discount discount);
}