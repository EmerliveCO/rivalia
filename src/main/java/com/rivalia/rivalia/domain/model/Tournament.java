package com.rivalia.rivalia.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public class Tournament {
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
    private String sportId;
    private String moneyInformationId;
    private String tournamentCountry;

    public Tournament(Long id, String tournamentName, String ownerId, String description, LocalDateTime timeStamp,
                      LocalDate createdAt, LocalDate tournamentDay, LocalTime tournamentHour, String status, String inscriptions,
                      Integer price, String typeTournament, String contactNumberIndicative, String contactNumber, Boolean requirePassword,
                      String password, String sportId, String moneyInformationId, String tournamentCountry) {
        this.id = id;
        this.tournamentName = tournamentName;
        this.ownerId = ownerId;
        this.description = description;
        this.timeStamp = timeStamp;
        this.createdAt = createdAt;
        this.tournamentDay = tournamentDay;
        this.tournamentHour = tournamentHour;
        this.status = status;
        this.inscriptions = inscriptions;
        this.price = price;
        this.typeTournament = typeTournament;
        this.contactNumberIndicative = contactNumberIndicative;
        this.contactNumber = contactNumber;
        this.requirePassword = requirePassword;
        this.password = password;
        this.sportId = sportId;
        this.moneyInformationId = moneyInformationId;
        this.tournamentCountry = tournamentCountry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getTournamentDay() {
        return tournamentDay;
    }

    public void setTournamentDay(LocalDate tournamentDay) {
        this.tournamentDay = tournamentDay;
    }

    public LocalTime getTournamentHour() {
        return tournamentHour;
    }

    public void setTournamentHour(LocalTime tournamentHour) {
        this.tournamentHour = tournamentHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(String inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTypeTournament() {
        return typeTournament;
    }

    public void setTypeTournament(String typeTournament) {
        this.typeTournament = typeTournament;
    }

    public String getContactNumberIndicative() {
        return contactNumberIndicative;
    }

    public void setContactNumberIndicative(String contactNumberIndicative) {
        this.contactNumberIndicative = contactNumberIndicative;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Boolean getRequirePassword() {
        return requirePassword;
    }

    public void setRequirePassword(Boolean requirePassword) {
        this.requirePassword = requirePassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getMoneyInformationId() {
        return moneyInformationId;
    }

    public void setMoneyInformationId(String moneyInformationId) {
        this.moneyInformationId = moneyInformationId;
    }

    public String getTournamentCountry() {
        return tournamentCountry;
    }

    public void setTournamentCountry(String tournamentCountry) {
        this.tournamentCountry = tournamentCountry;
    }
}
