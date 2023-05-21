package com.example.pincommunity.servicies;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClubService {
    ResponseEntity<ClubDto> createClub(CreateClubDto createClubDto);

    ResponseEntity<ClubDto> updateClub(Long id, ClubDto clubDto);

    /**
     * @throws ClubNotFoundException status 404
     */
    ResponseEntity<Void> updateClubAvatar(Long id, MultipartFile file);

    /**
     * @throws ClubNotFoundException status 404
     */
    ResponseEntity<ClubDto> getClubById(Long id);

    ResponseEntity<List<ClubDto>> getAllClubs();

    List<ClubDto> getAllClubsH();
}
