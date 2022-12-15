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
@Table(name = "pinsets")
public class Pinset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pinsets_name")
    private String pinsetName;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture pinsetPictureId;

    @JsonIgnore
    @OneToMany(mappedBy = "pinset")
    private List<Pin> associatedPins;
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club originClub;

}
