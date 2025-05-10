package com.travelvn.tourbookingsytem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiAdResponse<T> {
    private int code = 1000;
    private String message;
    private T result;
}
