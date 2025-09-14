package com.rivalia.rivalia.infraestructure.inbound.dto;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginAppRequest {
    private String appName;
    private String password;
}
