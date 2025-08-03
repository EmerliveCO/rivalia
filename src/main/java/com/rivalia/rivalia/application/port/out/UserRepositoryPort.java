package com.rivalia.rivalia.application.port.out;

import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.outbund.database.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {
    Mono<UserEntity> save(User user);
}
