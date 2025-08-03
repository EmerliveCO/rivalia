package com.rivalia.rivalia.infraestructure.inbound.controllers;

import com.rivalia.rivalia.application.port.in.UserUseCase;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.inbound.dto.UserRequest;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final GlobalMapper globalMapper;

    public UserController(UserUseCase userUseCase, GlobalMapper globalMapper) {
        this.userUseCase = userUseCase;
        this.globalMapper = globalMapper;
    }

    @PostMapping
    public Mono<ResponseEntity<User>> save(@RequestBody Mono<UserRequest> userRequest) {
        return userRequest.
                map(user -> globalMapper.map(userRequest, User.class))
                .flatMap(userUseCase::save)
                .map(userSaved -> ResponseEntity
                        .created(URI.create("/users/" + userSaved.getId()))
                        .body(userSaved));
    }
}
