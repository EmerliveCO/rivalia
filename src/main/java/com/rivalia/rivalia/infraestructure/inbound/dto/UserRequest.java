package com.rivalia.rivalia.infraestructure.inbound.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserRequest {
    private Long id;
    private String idAuth;
    private String name;
    private String lastName;
    private LocalDateTime timeStamp;
    private LocalDate createdAt;
    private String status;
    private String city;
    private String province;
    private String country;
    private String clubName;
    private String league;
    private LocalDate bornDate;
    private String documentType;
    private String documentNumber;
    private String address;
    private String email;
    private String gender;
    private Boolean isDeleted;
}
