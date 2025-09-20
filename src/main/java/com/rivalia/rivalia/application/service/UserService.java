package com.rivalia.rivalia.application.service;

import com.rivalia.rivalia.application.port.in.UserUseCase;
import com.rivalia.rivalia.application.port.out.UserRepositoryPort;
import com.rivalia.rivalia.application.port.out.WebClientRepositoryPort;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements UserUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final WebClientRepositoryPort webClientRepositoryPort;
    private final GlobalMapper globalMapper;

    public UserService(UserRepositoryPort userRepositoryPort, WebClientRepositoryPort webClientRepositoryPort, GlobalMapper globalMapper) {
        this.userRepositoryPort = userRepositoryPort;
        this.webClientRepositoryPort = webClientRepositoryPort;
        this.globalMapper = globalMapper;
    }

    @Override
    public Mono<User> save(User user, String appId, String password) {
        return webClientRepositoryPort.saveInAuthApi(user, appId, password)
                .map(authResponse -> {
                    user.setIdAuth(authResponse.getData().getUserId());
                    return user;
                })
                .flatMap(userRepositoryPort::save)
                .map(userSaved -> globalMapper.map(userSaved, User.class));
    }

    @Override
    public Mono<User> edit(User user, String token) {
        return webClientRepositoryPort.validateUserAuth(token)
                .map(idAuth -> {
                    user.setIdAuth(idAuth.getData().getIdAuth());
                    return user;
                })
                .flatMap(userRepositoryPort::edit)
                .map(userEdited -> globalMapper.map(userEdited, User.class));
    }
}
