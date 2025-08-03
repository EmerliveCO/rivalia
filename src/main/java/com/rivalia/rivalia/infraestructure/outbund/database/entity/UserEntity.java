package com.rivalia.rivalia.infraestructure.outbund.database.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
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
