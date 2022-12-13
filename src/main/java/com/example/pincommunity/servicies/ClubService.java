package com.example.pincommunity.servicies;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import org.springframework.http.ResponseEntity;

public interface ClubService {
    ResponseEntity<ClubDto> createClub(CreateClubDto createClubDto);
    ResponseEntity<ClubDto> updateClub(Long id, ClubDto clubDto);
}
