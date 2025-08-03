package com.rivalia.rivalia.application.port.out;

import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiUserSave;
import reactor.core.publisher.Mono;

public interface WebClientRepositoryPort {
    Mono<AuthApiGeneralResponse<AuthApiUserSave>> saveInAuthApi(User user);
    Mono<AuthApiGeneralResponse<Object>> deleteAuthApiUser(String id);
}
