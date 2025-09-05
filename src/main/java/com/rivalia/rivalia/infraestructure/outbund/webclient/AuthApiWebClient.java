package com.rivalia.rivalia.infraestructure.outbund.webclient;

import com.rivalia.rivalia.application.port.out.WebClientRepositoryPort;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.inbound.dto.LoginAppRequest;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiUserSave;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public class AuthApiWebClient implements WebClientRepositoryPort {

    private static final String DEFAULT_URI_USERS = "/api/v1/users";
    private static final String APP_ID_VERIFY = "686d406dc46e5ec329ce22ab";
    private static final String APP_NAME = "TESTAPP";
    private static final String APP_PASSWORD = "MTIzNDU2Nzg";

    private final WebClient webClient;

    public AuthApiWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<AuthApiGeneralResponse<AuthApiUserSave>> saveInAuthApi(User user) {
        return dynamicWebClient(HttpMethod.POST, DEFAULT_URI_USERS, null, null,
                null, user, new ParameterizedTypeReference<AuthApiGeneralResponse<AuthApiUserSave>>(){});
    }

    @Override
    public Mono<AuthApiGeneralResponse<Object>> deleteAuthApiUser(String id) {
        return loginAppAdmin()
                .flatMap(loginData -> dynamicWebClient(HttpMethod.DELETE, "/api/v1/app/deleteUser", null
                        ,Map.of("userId", id), Map.of("Authorization", "Bearer " + loginData.getData(), "app-id-verify", APP_ID_VERIFY),
                        null, new ParameterizedTypeReference<>() {}));
    }

    public Mono<AuthApiGeneralResponse<String>> loginAppAdmin() {
        LoginAppRequest loginAppRequest = LoginAppRequest.builder()
                .appName(APP_NAME)
                .password(APP_PASSWORD)
                .build();
        return dynamicWebClient(HttpMethod.POST, "/api/v1/auth/app/login", null, null, null,
                loginAppRequest,new ParameterizedTypeReference<AuthApiGeneralResponse<String>>(){});
    }

    public <T, R>Mono<AuthApiGeneralResponse<R>> dynamicWebClient(HttpMethod method,
                                                            String url,
                                                            Map<String, ?> pathParams,
                                                            Map<String, ?> queryParams,
                                                            Map<String, String> headers,
                                                            T requestBody,
                                                            ParameterizedTypeReference<AuthApiGeneralResponse<R>> responseType) {
        WebClient.RequestBodySpec requestSpec = webClient
                .method(method)
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path(url);
                    if (queryParams != null) {
                        queryParams.forEach(builder::queryParam);
                    }
                    return builder.build(pathParams == null ? Map.of() : pathParams);
                })
                .contentType(MediaType.APPLICATION_JSON);

        if (headers != null) {
            requestSpec.headers(header -> headers.forEach(header::add));
        }

        if (requestBody != null) {
            return requestSpec
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(responseType);
        } else {
            return requestSpec
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response ->
                            response.bodyToMono(AuthApiGeneralResponse.class).flatMap(error ->
                                    Mono.error(new RuntimeException(error.getMessage()))))
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            Mono.error(new RuntimeException("Server Error")))
                    .bodyToMono(responseType);
        }
    }


}
