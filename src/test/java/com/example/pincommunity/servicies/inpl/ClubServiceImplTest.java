package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.exceptions.ClubAlreadyExists;
import com.example.pincommunity.repositories.ClubRepository;
import com.example.pincommunity.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @InjectMocks
    ClubServiceImpl clubService;

    @BeforeEach
    void setUp() {
        CREATE_CLUB_DTO.setCity("Berlin");

    }

    @Test
    void createClub_whenClubExists_thenThrowClubAlreadyExists() {

        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));
        assertThrows(ClubAlreadyExists.class, () -> clubService.createClub(CREATE_CLUB_DTO));
    }

    @Test
    void updateClub() {
    }
}