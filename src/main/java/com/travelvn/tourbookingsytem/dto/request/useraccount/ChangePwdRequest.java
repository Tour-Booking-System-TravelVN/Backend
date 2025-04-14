package com.travelvn.tourbookingsytem.dto.request.useraccount;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChangePwdRequest {
    @Size(min = 6, max = 20, message = "PASSWORD_INVALID")
    @NotNull
    private String oldPassword;

    @Size(min = 6, max = 20, message = "PASSWORD_INVALID")
    @NotNull
    private String newPassword;
}
