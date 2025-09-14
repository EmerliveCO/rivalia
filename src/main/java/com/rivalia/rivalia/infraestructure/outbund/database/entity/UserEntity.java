package com.rivalia.rivalia.infraestructure.outbund.database.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;
    @Column("id_auth")
    private String idAuth;
    private String name;
    @Column("last_name")
    private String lastName;
    @Column("time_stamp")
    private LocalDate timeStamp;
    @Column("created_at")
    private LocalDate createdAt;
    private String status;
    private String city;
    private String province;
    private String country;
    @Column("club_name")
    private String clubName;
    private String league;
    @Column("born_date")
    private LocalDate bornDate;
    @Column("document_type")
    private String documentType;
    @Column("document_number")
    private String documentNumber;
    private String address;
    private String email;
    private String gender;
    @Column("isDeleted")
    private Boolean isDeleted;

}
