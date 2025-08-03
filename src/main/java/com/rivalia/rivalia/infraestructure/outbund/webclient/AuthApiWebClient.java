package com.rivalia.rivalia.infraestructure.outbund.webclient;

import com.rivalia.rivalia.application.port.out.WebClientRepositoryPort;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiUserSave;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class AuthApiWebClient implements WebClientRepositoryPort {
    private final WebClient webClient;

    public AuthApiWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<AuthApiGeneralResponse<AuthApiUserSave>> saveInAuthApi(User user) {
        return webClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(AuthApiGeneralResponse.class).flatMap(error ->
                                Mono.error(new RuntimeException(error.getMessage()))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("Error del servidor")))
                .bodyToMono(new ParameterizedTypeReference<AuthApiGeneralResponse<AuthApiUserSave>>(){});
    }

    @Override
    public Mono<AuthApiGeneralResponse<Object>> deleteAuthApiUser(String id) {
        return webClient
                .delete()
                .uri("/api/v1/users/{}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(AuthApiGeneralResponse.class).flatMap(error ->
                                Mono.error(new RuntimeException(error.getMessage()))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("Error del servidor")))
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }


}
