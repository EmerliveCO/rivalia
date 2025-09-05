package com.rivalia.rivalia.application.port.out;

import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiUserSave;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface WebClientRepositoryPort {
    Mono<AuthApiGeneralResponse<AuthApiUserSave>> saveInAuthApi(User user);
    Mono<AuthApiGeneralResponse<Object>> deleteAuthApiUser(String id);
}
