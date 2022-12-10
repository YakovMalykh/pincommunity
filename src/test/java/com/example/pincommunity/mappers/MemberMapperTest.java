package com.example.pincommunity.mappers;

import com.example.pincommunity.Constatnts.ConstantsForTests;
import com.example.pincommunity.constants.Role;
import com.example.pincommunity.dto.MemberDto;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.ClubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static com.example.pincommunity.constants.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberMapperTest {
    @Mock
    ClubRepository clubRepository;
    @InjectMocks
    MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);

    @BeforeEach
    void setUp() {
        CREATE_MEMBER_DTO.setEmail(EMAIL);
        CREATE_MEMBER_DTO.setPassword(ENCODED_PASSWORD);
        CREATE_MEMBER_DTO.setFullName(FULL_NAME);
        CREATE_MEMBER_DTO.setRole(USER);

        MEMBER_DTO.setId(1L);
        MEMBER_DTO.setEmail(EMAIL);
        MEMBER_DTO.setFullName(FULL_NAME);
        MEMBER_DTO.setBirthday(TEST_DATE);
        MEMBER_DTO.setAvatarUrl(TEST_URL);
        MEMBER_DTO.setClubCity(CLUB_CITY);

        MEMBER_2.setId(2L);
        MEMBER_2.setUsername(EMAIL);
        MEMBER_2.setFullName(FULL_NAME);
        MEMBER_2.setBirthday(TEST_DATE);
        MEMBER_2.setAvatar(AVATAR);
        MEMBER_2.setCurrentClub(CLUB);

        AVATAR.setAvatarUrl(TEST_URL);
        CLUB.setCity(CLUB_CITY);

    }

    @Test
    void createMemberDtoToMember() {
        Member member = memberMapper.createMemberDtoToMember(CREATE_MEMBER_DTO);
        assertEquals(EMAIL, member.getUsername());
        assertEquals(ENCODED_PASSWORD, member.getPassword());
        assertEquals(FULL_NAME, member.getFullName());
        assertEquals(USER.toString(), member.getRole());

    }

    @Test
    void updateMemberFromMemberDto_whenSuccessful() {
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));
        memberMapper.updateMemberFromMemberDto(MEMBER_DTO, MEMBER);
        assertNull(MEMBER.getId());
        assertNull(MEMBER.getUsername());
        assertEquals(FULL_NAME, MEMBER.getFullName());
        assertEquals(TEST_DATE, MEMBER.getBirthday());
        assertNull(MEMBER.getAvatar());
        assertEquals(CLUB, MEMBER.getCurrentClub());
    }

    @Test
    void updateMemberFromMemberDto_whenFailed() {
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.empty());
        ClubNotFoundException exception = assertThrows(ClubNotFoundException.class, () -> {
            memberMapper.updateMemberFromMemberDto(MEMBER_DTO, MEMBER);
        });
        String[] stringsArray = exception.getMessage().split(" ");
        assertEquals("Club", stringsArray[0]);
    }

    @Test
    void memberToMemberDto() {
        MemberDto memberDto = memberMapper.memberToMemberDto(MEMBER_2);
        assertEquals(2L, memberDto.getId());
        assertEquals(EMAIL, memberDto.getEmail());
        assertEquals(FULL_NAME, memberDto.getFullName());
        assertEquals(TEST_DATE, memberDto.getBirthday());
        assertEquals(TEST_URL, memberDto.getAvatarUrl());
        assertEquals(CLUB_CITY, memberDto.getClubCity());
    }

    @Test
    void avatarToString_whenAvatarIsNull() {
        String avatarUrl = memberMapper.avatarToString(null);
        assertEquals(null, avatarUrl);
    }

    @Test
    void clubToString_whenClubIsNull() {
        String clubCity = memberMapper.clubToString(null);
        assertEquals(null, clubCity);
    }
}