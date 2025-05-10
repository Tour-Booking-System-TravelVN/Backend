package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.response.CatergoryAdResponse;
import com.travelvn.tourbookingsytem.model.Category;
import com.travelvn.tourbookingsytem.dto.request.CatergoryAdRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryAdMapper {
    Category toCategory(CatergoryAdRequest catergoryAdRequest);
//    Category toCategory(CatergoryResponse catergoryResponse);

//    CatergoryRequest toCatergoryRequest(Category category);
    CatergoryAdResponse toCatergoryResponse(Category category);
}
