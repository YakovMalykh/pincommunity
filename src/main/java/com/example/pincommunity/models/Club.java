package com.example.pincommunity.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    @OneToOne
    @JoinColumn(name = "clubs_admin")
    private Member admin;

//    @OneToMany(mappedBy = "currentClub")
//    private List<Member> members;
//    @OneToMany(mappedBy = "originClub")
//    private List<Pin> releasedPins;
//    @OneToMany(mappedBy = "originClub")
//    private List<Pinset> releasedPinsets;

}
