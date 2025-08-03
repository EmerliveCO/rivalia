package com.rivalia.rivalia.infraestructure.inbound.controllers;

import com.rivalia.rivalia.application.port.in.TournamentUseCase;
import com.rivalia.rivalia.domain.model.Tournament;
import com.rivalia.rivalia.infraestructure.inbound.dto.TournamentRequest;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/tournament")
public class TournamentController {

    private final TournamentUseCase tournamentUseCase;
    private final GlobalMapper globalMapper;

    public TournamentController(TournamentUseCase tournamentUseCase, GlobalMapper globalMapper) {
        this.tournamentUseCase = tournamentUseCase;
        this.globalMapper = globalMapper;
    }

    @PostMapping
    public Mono<ResponseEntity<Tournament>> save(@RequestBody Mono<TournamentRequest> tournamentRequest) {
        return tournamentRequest
                .map(tournament -> globalMapper.map(tournament, Tournament.class))
                .flatMap(tournamentUseCase::save)
                .map(tournamentSaved -> ResponseEntity
                        .created(URI.create("/tournament/" + tournamentSaved.getId()))
                        .body(tournamentSaved));
    }
}
