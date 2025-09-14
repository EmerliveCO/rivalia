package com.rivalia.rivalia.application.service;

import com.rivalia.rivalia.application.port.in.TournamentUseCase;
import com.rivalia.rivalia.application.port.out.TournamentRepositoryPort;
import com.rivalia.rivalia.application.port.out.WebClientRepositoryPort;
import com.rivalia.rivalia.domain.model.Tournament;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TournamentService  implements TournamentUseCase {
    private final TournamentRepositoryPort tournamentRepositoryPort;
    private final WebClientRepositoryPort webClientRepositoryPort;
    private final GlobalMapper globalMapper;

    @Override
    public Mono<Tournament> save(Tournament tournament, String token) {
        return webClientRepositoryPort.isUserAuthorized(token)
                .flatMap(authorized -> tournamentRepositoryPort.save(tournament)
                        .map(tournamentSaved -> globalMapper.map(tournamentSaved, Tournament.class)));
    }
}
