package com.example.pincommunity.repositories;

import com.example.pincommunity.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByCityIgnoreCase(String city);
}
