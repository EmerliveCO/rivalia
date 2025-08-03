package com.rivalia.rivalia.application.service;

import com.rivalia.rivalia.application.port.in.TournamentUseCase;
import com.rivalia.rivalia.application.port.out.TournamentRepositoryPort;
import com.rivalia.rivalia.domain.model.Tournament;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TournamentService  implements TournamentUseCase {
    private final TournamentRepositoryPort tournamentRepositoryPort;
    private final GlobalMapper globalMapper;

    public TournamentService(TournamentRepositoryPort tournamentRepositoryPort, GlobalMapper globalMapper) {
        this.tournamentRepositoryPort = tournamentRepositoryPort;
        this.globalMapper = globalMapper;
    }

    @Override
    public Mono<Tournament> save(Tournament tournament) {
        return tournamentRepositoryPort.save(tournament)
                .map(tournamentSaved -> globalMapper.map(tournamentSaved, Tournament.class));
    }
}
