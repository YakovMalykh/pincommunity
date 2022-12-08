package com.example.pincommunity.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String role;
    private String password;
    @Column(name = "full_name")
    private String fullName;

    private LocalDate birthday;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @OneToOne
    @JoinColumn(name = "club_id")
    private Club currentClub;

    @JsonIgnore
    @OneToMany(mappedBy = "holder")
    private List<Pin> pingalery;


}
