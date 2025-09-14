package com.rivalia.rivalia.infraestructure.inbound.controllers;

import com.rivalia.rivalia.application.port.in.UserUseCase;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.inbound.dto.UserRequest;
import com.rivalia.rivalia.infraestructure.outbund.database.UserRepositoryAdapter;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;
    private final GlobalMapper globalMapper;
    private final UserRepositoryAdapter userRepositoryAdapter;

    @PostMapping
    public Mono<ResponseEntity<User>> save(@RequestBody Mono<UserRequest> userRequest,
                                           @RequestHeader("app-id") String appId,
                                           @RequestHeader("password") String password) {
        return userRequest
                .map(user -> globalMapper.map(user, User.class))
                .flatMap(userToSave -> userUseCase.save(userToSave, appId, password))
                .map(userSaved -> ResponseEntity
                        .created(URI.create("/users/" + userSaved.getId()))
                        .body(userSaved));
    }
}
