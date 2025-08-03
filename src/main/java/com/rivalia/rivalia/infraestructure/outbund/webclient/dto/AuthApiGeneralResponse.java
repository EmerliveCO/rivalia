package com.rivalia.rivalia.infraestructure.outbund.webclient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthApiGeneralResponse<T> {
    private String status;
    private String message;
    private T data;
}
