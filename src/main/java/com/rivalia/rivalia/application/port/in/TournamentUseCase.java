package com.rivalia.rivalia.application.port.in;

import com.rivalia.rivalia.domain.model.Tournament;
import reactor.core.publisher.Mono;

public interface TournamentUseCase {
    Mono<Tournament> save(Tournament tournament, String token);
}
