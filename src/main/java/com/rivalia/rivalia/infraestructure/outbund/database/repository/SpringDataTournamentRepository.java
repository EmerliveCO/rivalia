package com.rivalia.rivalia.infraestructure.outbund.database.repository;

import com.rivalia.rivalia.infraestructure.outbund.database.entity.TournamentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpringDataTournamentRepository extends ReactiveCrudRepository<TournamentEntity, Long> {
}
