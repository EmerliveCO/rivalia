package com.rivalia.rivalia.infraestructure.outbund.database;

import com.rivalia.rivalia.application.port.out.UserRepositoryPort;
import com.rivalia.rivalia.domain.model.User;
import com.rivalia.rivalia.infraestructure.outbund.database.entity.UserEntity;
import com.rivalia.rivalia.infraestructure.outbund.database.repository.SpringDataUserRepository;
import com.rivalia.rivalia.infraestructure.outbund.webclient.AuthApiWebClient;
import com.rivalia.rivalia.shared.exception.DatabaseException;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

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
        UserEntity userEntity = globalMapper.map(user, UserEntity.class);
        userEntity.setTimeStamp(LocalDate.now());
        userEntity.setCreatedAt(LocalDate.now());
        userEntity.setStatus("Active");
        userEntity.setIsDeleted(false);

        return userRepository.save(userEntity)
                .onErrorResume(error -> authApiWebClient.deleteAuthApiUser(user.getIdAuth())
                            .then(Mono.error(new DatabaseException(error.getCause().getLocalizedMessage()))));
    }
}
