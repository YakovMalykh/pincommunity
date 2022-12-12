package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.repositories.ClubRepository;
import com.example.pincommunity.servicies.ClubService;
import org.springframework.http.ResponseEntity;

public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public ResponseEntity<ClubDto> createClub(CreateClubDto createClubDto) {
        return null;
    }

    @Override
    public ResponseEntity<ClubDto> updateClub(Long id, CreateClubDto createClubDto) {
        return null;
    }
}
