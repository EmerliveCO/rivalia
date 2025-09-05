package com.rivalia.rivalia.infraestructure.inbound.dto;

import lombok.Builder;

@Builder
public class LoginAppRequest {
    private String appName;
    private String password;
}
