package com.rivalia.rivalia.shared.exception;

import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{
    private final HttpStatus status;
    private final Object response;

    public ApiException(HttpStatus status, Object response) {
        super(response instanceof AuthApiGeneralResponse ? ((AuthApiGeneralResponse<?>) response).getMessage() : "API Error");
        this.status = status;
        this.response = response;

    }

    public HttpStatus getStatus() {
        return status;
    }

    public Object getResponse() {
        return response;
    }
}
