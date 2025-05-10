package com.travelvn.tourbookingsytem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAccountAdRequest {
    @NotBlank
    @Size(max = 40)
    private String username;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    private Integer customerId;
    private Integer administratorId;
    private Integer tourGuideId;
    private Integer tourOperatorId;

    @NotBlank
    @Size(max = 4)
    private String status;

    @NotBlank
    @Email
    private String email;
}