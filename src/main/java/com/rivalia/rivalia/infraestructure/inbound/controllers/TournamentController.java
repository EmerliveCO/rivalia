package com.rivalia.rivalia.infraestructure.inbound.controllers;

import com.rivalia.rivalia.application.port.in.TournamentUseCase;
import com.rivalia.rivalia.domain.model.Tournament;
import com.rivalia.rivalia.infraestructure.inbound.dto.TournamentRequest;
import com.rivalia.rivalia.shared.mapper.GlobalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentUseCase tournamentUseCase;
    private final GlobalMapper globalMapper;

    @PostMapping
    public Mono<ResponseEntity<Tournament>> save(@RequestBody Mono<TournamentRequest> tournamentRequest,
                                                 @RequestHeader("Authorization") String token) {
        return tournamentRequest
                .map(tournament -> globalMapper.map(tournament, Tournament.class))
                .flatMap(tournament -> tournamentUseCase.save(tournament, token))
                .map(tournamentSaved -> ResponseEntity
                        .created(URI.create("/tournament/" + tournamentSaved.getId()))
                        .body(tournamentSaved));
    }
}
