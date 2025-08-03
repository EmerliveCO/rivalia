package com.rivalia.rivalia.application.port.in;

import com.rivalia.rivalia.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserUseCase {
    Mono<User> save(User user);
}
