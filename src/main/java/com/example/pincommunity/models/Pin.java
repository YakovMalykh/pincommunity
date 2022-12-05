package com.example.pincommunity.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "pins")
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pins_name")
    private String pinsName;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club originClub;
    @ManyToOne
    @JoinColumn(name = "pinset_id")
    private Pinset pinset;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member holder;

}
