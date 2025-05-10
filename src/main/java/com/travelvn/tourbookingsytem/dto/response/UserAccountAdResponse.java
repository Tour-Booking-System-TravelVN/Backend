package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAccountAdResponse {
    private String username;
    private String status;
    private String email;
    private Integer customerId;
    private Integer administratorId;
    private Integer tourGuideId;
    private Integer tourOperatorId;
}