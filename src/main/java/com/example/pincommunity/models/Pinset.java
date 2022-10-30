package com.example.pincommunity.models;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Pinset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate releasedDate;
    private String picturePath;
    @OneToMany(mappedBy = "pinset")
    private List<Pin> associatedPins;
    @ManyToOne
    private Club originClub;

    public Pinset() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<Pin> getAssociatedPins() {
        return associatedPins;
    }

    public void setAssociatedPins(List<Pin> associatedPins) {
        this.associatedPins = associatedPins;
    }

    public Club getOriginClub() {
        return originClub;
    }

    public void setOriginClub(Club originClub) {
        this.originClub = originClub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pinset pinset = (Pinset) o;
        return id.equals(pinset.id) && name.equals(pinset.name) && releasedDate.equals(pinset.releasedDate) && associatedPins.equals(pinset.associatedPins) && originClub.equals(pinset.originClub);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releasedDate, associatedPins, originClub);
    }

    @Override
    public String toString() {
        return "Pinset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releasedDate=" + releasedDate +
                ", picturePath='" + picturePath + '\'' +
                ", originClub=" + originClub +
                '}';
    }
}
