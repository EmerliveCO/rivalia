package com.rivalia.rivalia.infraestructure.outbund.database.repository;

import com.rivalia.rivalia.infraestructure.outbund.database.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpringDataUserRepository extends ReactiveCrudRepository<UserEntity, Long> {
}
