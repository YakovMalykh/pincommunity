package com.example.pincommunity.repositories;

import com.example.pincommunity.models.Pinset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinsetRepository extends JpaRepository<Pinset,Long> {
    Optional<Pinset> findByPinsetNameIgnoreCase(String pinsetName);
}
