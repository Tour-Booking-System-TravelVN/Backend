package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CategoryRequest;
import com.travelvn.tourbookingsytem.dto.response.CategoryResponse;
import com.travelvn.tourbookingsytem.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest catergoryRequest);
//    Category toCategory(CatergoryResponse catergoryResponse);

//    CatergoryRequest toCatergoryRequest(Category category);
//    @Mapping(target = "tourSet", ignore = true)
    CategoryResponse toCatergoryResponse(Category category);
}
