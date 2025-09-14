package com.rivalia.rivalia.infraestructure.inbound.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class TournamentRequest {

    private String tournamentName;
    private String ownerId;
    private String description;
    private LocalDateTime timeStamp;
    private LocalDate createdAt;
    private LocalDate tournamentDay;
    private LocalTime tournamentHour;
    private String status;
    private String inscriptions;
    private Integer price;
    private String typeTournament;
    private String contactNumberIndicative;
    private String contactNumber;
    private Boolean requirePassword;
    private String password;
    private String sportId;
    private String moneyInformationId;
    private String tournamentCountry;

 }
