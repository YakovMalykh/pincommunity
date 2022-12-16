package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.exceptions.ClubAlreadyExists;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.mappers.ClubMapper;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.ClubRepository;
import com.example.pincommunity.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClubServiceImplTest {
    @Mock
    ClubRepository clubRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ClubMapper clubMapper;
    @Mock
    AvatarServiceImpl avatarService;
    @InjectMocks
    ClubServiceImpl clubService;

    @BeforeEach
    void setUp() {
        CREATE_CLUB_DTO.setCity("Berlin");

        CLUB.setAdmin(MEMBER);
    }

    @Test
    void createClub_whenClubExists_thenThrowClubAlreadyExists() {

        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));
        assertThrows(ClubAlreadyExists.class, () -> clubService.createClub(CREATE_CLUB_DTO));
    }

    @Test
    void createClub_successful() {
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(clubMapper.createClubDtoToClub(any(CreateClubDto.class))).thenReturn(CLUB);
        when(clubRepository.save(any(Club.class))).thenReturn(CLUB);
        when(memberRepository.save(any(Member.class))).thenReturn(MEMBER);
        when(clubMapper.clubToClubDto(any(Club.class))).thenReturn(CLUB_DTO);

        ResponseEntity<ClubDto> response = clubService.createClub(CREATE_CLUB_DTO);

        assertEquals(ResponseEntity.ok(CLUB_DTO), response);
    }

    @Test
    void updateClub_whenClubExists_thenThrowClubAlreadyExists() {
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ClubNotFoundException.class, () -> clubService.updateClub(1L, CLUB_DTO));
    }

    @Test
    void updateClub_successful() {
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(CLUB));
        when(memberRepository.save(any(Member.class))).thenReturn(MEMBER);
        doNothing().when(clubMapper).updateClubFromClubDto(any(ClubDto.class), any(Club.class));
        when(clubRepository.save(any(Club.class))).thenReturn(CLUB);
        when(clubMapper.clubToClubDto(any(Club.class))).thenReturn(CLUB_DTO);

        ResponseEntity<ClubDto> response = clubService.updateClub(1L, CLUB_DTO);

        assertEquals(ResponseEntity.ok(CLUB_DTO), response);
    }

    @Test
    void updateClubAvatar_chouldThrowClubNotFoundException() {
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClubNotFoundException.class, () -> clubService.updateClubAvatar(1L, FILE));
    }

    @Test
    void updateClubAvatar_successfully() {
        CLUB.setClubAvatar(AVATAR);
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(CLUB));
        when(avatarService.updateImage(any(Avatar.class), any(MultipartFile.class))).thenReturn(AVATAR);

        ResponseEntity<Void> response = clubService.updateClubAvatar(1L, FILE);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void getClubById_successfully() {
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(CLUB));
        when(clubMapper.clubToClubDto(any(Club.class))).thenReturn(CLUB_DTO);

        ResponseEntity<ClubDto> response = clubService.getClubById(1L);
        assertEquals(ResponseEntity.ok(CLUB_DTO), response);
    }

    @Test
    void getClubById_shouldThrowClubNotFoundException() {
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ClubNotFoundException.class, () -> clubService.getClubById(1L));
    }

}