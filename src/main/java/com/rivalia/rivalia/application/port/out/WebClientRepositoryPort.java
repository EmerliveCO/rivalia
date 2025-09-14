package com.rivalia.rivalia.application.port.out;

import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiUserSave;
import reactor.core.publisher.Mono;

public interface WebClientRepositoryPort {
    Mono<AuthApiGeneralResponse<AuthApiUserSave>> saveInAuthApi(User user, String appId, String password);
    Mono<AuthApiGeneralResponse<Object>> deleteAuthApiUser(String id);
    Mono<AuthApiGeneralResponse<Void>> isUserAuthorized(String token);
}
