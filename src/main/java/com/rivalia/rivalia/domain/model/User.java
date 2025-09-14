package com.rivalia.rivalia.domain.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String idAuth;
    private String name;
    private String lastName;
    private LocalDate timeStamp;
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
