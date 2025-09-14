package com.rivalia.rivalia.infraestructure.outbund.webclient.dto;

import lombok.*;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CreateUserRequestBody {
    private String email;
    private String password;
    private String appId;
    private String name;
    private String lastName;
}
