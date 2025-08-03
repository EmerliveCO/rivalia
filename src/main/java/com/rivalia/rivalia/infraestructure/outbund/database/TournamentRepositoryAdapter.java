package com.rivalia.rivalia.infraestructure.outbund.database;

import com.rivalia.rivalia.application.port.out.TournamentRepositoryPort;
import com.rivalia.rivalia.domain.model.Tournament;
import com.rivalia.rivalia.infraestructure.outbund.database.entity.TournamentEntity;
import com.rivalia.rivalia.infraestructure.outbund.database.repository.SpringDataTournamentRepository;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TournamentRepositoryAdapter implements TournamentRepositoryPort {

    private final SpringDataTournamentRepository tournamentRepository;
    private final GlobalMapper globalMapper;

    public TournamentRepositoryAdapter(SpringDataTournamentRepository springDataTournamentRepository, GlobalMapper globalMapper) {
        this.tournamentRepository = springDataTournamentRepository;
        this.globalMapper = globalMapper;
    }

    @Override
    public Mono<TournamentEntity> save(Tournament tournament) {
        return tournamentRepository.save(globalMapper.map(tournament, TournamentEntity.class));
    }
}
