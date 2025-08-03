package com.rivalia.rivalia.application.port.out;

import com.rivalia.rivalia.domain.model.Tournament;
import com.rivalia.rivalia.infraestructure.outbund.database.entity.TournamentEntity;
import reactor.core.publisher.Mono;

public interface TournamentRepositoryPort {
    Mono<TournamentEntity> save(Tournament tournament);
}
