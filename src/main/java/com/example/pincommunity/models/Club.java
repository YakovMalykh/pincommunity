package com.example.pincommunity.models;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;
    private String originCity;
    @OneToMany(mappedBy = "currentClub")
    private List<Member> members;
    @OneToMany(mappedBy = "originClub")
    private List<Pin> releasedPins;
    @OneToMany(mappedBy = "originClub")
    private List<Pinset> releasedPinsets;

    public Club() {
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Pin> getReleasedPins() {
        return releasedPins;
    }

    public void setReleasedPins(List<Pin> releasedPins) {
        this.releasedPins = releasedPins;
    }

    public List<Pinset> getReleasedPinsets() {
        return releasedPinsets;
    }

    public void setReleasedPinsets(List<Pinset> releasedPinsets) {
        this.releasedPinsets = releasedPinsets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return clubId.equals(club.clubId) && originCity.equals(club.originCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clubId, originCity);
    }
}
