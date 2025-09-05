package com.rivalia.rivalia.infraestructure.outbund.database;

import com.rivalia.rivalia.application.port.out.UserRepositoryPort;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.outbund.database.entity.UserEntity;
import com.rivalia.rivalia.infraestructure.outbund.database.repository.SpringDataUserRepository;
import com.rivalia.rivalia.infraestructure.outbund.webclient.AuthApiWebClient;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository userRepository;
    private final GlobalMapper globalMapper;
    private final AuthApiWebClient authApiWebClient;

    public UserRepositoryAdapter(SpringDataUserRepository userRepository, GlobalMapper globalMapper, AuthApiWebClient authApiWebClient) {
        this.userRepository = userRepository;
        this.globalMapper = globalMapper;
        this.authApiWebClient = authApiWebClient;
    }

    @Override
    public Mono<UserEntity> save(User user) {
        return userRepository.save(globalMapper.map(user, UserEntity.class))
                .onErrorResume(error -> authApiWebClient.deleteAuthApiUser(user.getIdAuth())
                        .then(Mono.error(new RuntimeException("Something went wrong creating user"))));
    }
}
