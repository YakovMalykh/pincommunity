package com.example.pincommunity.models;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pinId;
    private String name;
    private String picturePath;
    private LocalDate releaseDate;
    @ManyToOne
    private Club originClub;
    @ManyToOne
    private Pinset pinset;

    public Pin() {
    }

    public Long getPinId() {
        return pinId;
    }

    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Club getOriginClub() {
        return originClub;
    }

    public void setOriginClub(Club originClub) {
        this.originClub = originClub;
    }

    public Pinset getPinset() {
        return pinset;
    }

    public void setPinset(Pinset pinset) {
        this.pinset = pinset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pin pin = (Pin) o;
        return pinId.equals(pin.pinId) && name.equals(pin.name) && releaseDate.equals(pin.releaseDate) && originClub.equals(pin.originClub) && pinset.equals(pin.pinset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pinId, name, releaseDate, originClub, pinset);
    }

    @Override
    public String toString() {
        return "Pin{" +
                "pinId=" + pinId +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", originClub=" + originClub +
                ", pinset=" + pinset +
                '}';
    }
}
