package com.travelvn.tourbookingsytem.dto.response.lite;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanionCustomerResponseLite {
    Integer id;
    String fullName;
}
