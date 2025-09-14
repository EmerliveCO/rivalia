package com.rivalia.rivalia.infraestructure.outbund.database.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table("tournament")
public class TournamentEntity {

    @Id
    private Long id;
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
    private Long sportId;
    private String moneyInformationId;
    private Long paymentMethod;
    private String tournamentCountry;

}
