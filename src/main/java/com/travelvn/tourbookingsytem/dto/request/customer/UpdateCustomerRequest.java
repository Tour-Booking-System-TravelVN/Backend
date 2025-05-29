package com.travelvn.tourbookingsytem.dto.request.customer;

import com.travelvn.tourbookingsytem.dto.request.lite.CustomerRequestLite;
import com.travelvn.tourbookingsytem.validator.EmailConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class UpdateCustomerRequest{
    @EmailConstraint(message = "INVALID_EMAIL")
    private String email;

    private CustomerRequestLite c;
}
