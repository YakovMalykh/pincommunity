package com.example.pincommunity.models;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String photoPath;
    private String email;
    private String password;
    private LocalDate birthday;
    @ManyToOne
    private Club currentClub;
    @OneToMany
    private List<Pin> pingalery;
    private String role;

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Club getCurrentClub() {
        return currentClub;
    }

    public void setCurrentClub(Club currentClub) {
        this.currentClub = currentClub;
    }

    public List<Pin> getPingalery() {
        return pingalery;
    }

    public void setPingalery(List<Pin> pingalery) {
        this.pingalery = pingalery;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return username.equals(member.username) && email.equals(member.email) && birthday.equals(member.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, birthday);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", currentClub=" + currentClub +
                ", role=" + role +
                '}';
    }
}
