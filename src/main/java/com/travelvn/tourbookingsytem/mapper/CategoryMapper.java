package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.model.Category;
import com.travelvn.tourbookingsytem.dto.request.CatergoryRequest;
import com.travelvn.tourbookingsytem.dto.response.CatergoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CatergoryRequest catergoryRequest);
    Category toCategory(CatergoryResponse catergoryResponse);

    CatergoryRequest toCatergoryRequest(Category category);
    CatergoryResponse toCatergoryResponse(Category category);
}
