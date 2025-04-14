package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.request.customer.UpdateCustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.model.UserAccount;
import org.mapstruct.*;

@Named("UserAccountMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CustomerMapper.class, AdministatorMapper.class, TourGuideMapper.class, TourOperatorMapper.class})
public interface UserAccountMapper {

//    @Named("toUserAccountEmail")
//    @Mappings({
//            @Mapping(target = "c", ignore = true)
//    })
    //UserAccount toUserAccountEmail(UserAccountRequest userAccountRequest);

    //{"CustomerMapper","toCustomerToRegister"}
//    @Mappings({
//            @Mapping(target = "c", source = "c", qualifiedByName = "toCustomerToRegister")
//    })
    UserAccount toUserAccount(UserAccountRequest userAccountRequest);
//    UserAccount toUserAccount(UserAccountResponse userAccountResponse);

//    UserAccountRequest toUserAccountRequest(UserAccount userAccount);

//    @Mappings({
//            @Mapping(target = "c", qualifiedByName = {"CustomerMapper", "toCustomerResponseWithoutUserAccount"})
//    })
//    UserAccountResponse toUserAccountResponse(UserAccount userAccount);

//    @Named("toUserAccountResponseWithoutCustomer")
//    @Mappings({
//            @Mapping(target = "c", expression = "java(null)")
//    })

    //{"CustomerMapper","toCustomerResponseWithoutUserAccount"}
//    @Mappings({
//            @Mapping(target = "c", source = "c", qualifiedByName = "toCustomerResponseWithoutUserAccount")
//    })
    UserAccountResponse toUserAccountResponse(UserAccount userAccount);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserAccount(UpdateCustomerRequest updateCustomerRequest, @MappingTarget UserAccount userAccount);
//    void updateUserAccount(@MappingTarget UserAccount userAccount, UserAccountRequest userAccountRequest);
}
