package com.rivalia.rivalia.infraestructure.outbund.webclient;

import com.rivalia.rivalia.application.port.out.WebClientRepositoryPort;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.inbound.dto.LoginAppRequest;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiUserSave;
import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.CreateUserRequestBody;
import com.rivalia.rivalia.shared.exception.ApiException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public class AuthApiWebClient implements WebClientRepositoryPort {

    private static final String DEFAULT_URI_USERS = "/api/v1/auth/user";
    private static final String APP_ID_VERIFY = "686d406dc46e5ec329ce22ab";
    private static final String APP_NAME = "TESTAPP";
    private static final String APP_PASSWORD = "MTIzNDU2Nzg";

    private final WebClient webClient;

    public AuthApiWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<AuthApiGeneralResponse<AuthApiUserSave>> saveInAuthApi(User user, String appId, String password) {
        return dynamicWebClient(HttpMethod.POST, DEFAULT_URI_USERS, null, null,
                null, CreateUserRequestBody.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .appId(appId)
                        .password(password)
                        .lastName(user.getLastName())
                        .build(), new ParameterizedTypeReference<AuthApiGeneralResponse<AuthApiUserSave>>(){});
    }

    @Override
    public Mono<AuthApiGeneralResponse<Object>> deleteAuthApiUser(String id) {
        return loginAppAdmin()
                .flatMap(loginData -> dynamicWebClient(HttpMethod.DELETE, "/api/v1/auth/app/deleteUser", null
                        ,Map.of("userId", id), Map.of("Authorization", "Bearer " + loginData.getData(), "app-id-verify", APP_ID_VERIFY),
                        null, new ParameterizedTypeReference<>() {}));
    }

    @Override
    public Mono<AuthApiGeneralResponse<Void>> isUserAuthorized(String token) {
        return dynamicWebClient(HttpMethod.GET, "/api/v1/auth", null, null,
                Map.of("Authorization", token, "app-id-verify", APP_ID_VERIFY), null,
                new ParameterizedTypeReference<>(){});
    }


    public Mono<AuthApiGeneralResponse<String>> loginAppAdmin() {
        LoginAppRequest loginAppRequest = LoginAppRequest.builder()
                .appName(APP_NAME)
                .password(APP_PASSWORD)
                .build();
        return dynamicWebClient(HttpMethod.POST, "/api/v1/auth/app/login", null, null, null,
                loginAppRequest,new ParameterizedTypeReference<AuthApiGeneralResponse<String>>(){});
    }

    public <T, R> Mono<AuthApiGeneralResponse<R>> dynamicWebClient(HttpMethod method,
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
            requestSpec.bodyValue(requestBody);
        }

        return requestSpec
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(responseType);
                    }

                    return clientResponse.bodyToMono(responseType)
                            .flatMap(errorBody ->
                                    Mono.error(new ApiException(
                                            HttpStatus.valueOf(clientResponse.statusCode().value()),
                                            errorBody
                                            )));
                });
    }
}
