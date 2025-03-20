package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.DiscountRequest;
import com.travelvn.tourbookingsytem.dto.response.DiscountResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Discount;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface DiscountMapper {
    Discount toDiscount(DiscountRequest discountRequest);
    Discount toDiscount(DiscountResponse discountResponse);

    DiscountResponse toDiscountResponse(Discount discount);
    DiscountRequest toDiscountRequest(Discount discount);
}
