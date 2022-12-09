package com.example.pincommunity.mappers;

import com.example.pincommunity.Constatnts.ConstantsForTests;
import com.example.pincommunity.constants.Role;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.ClubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static com.example.pincommunity.constants.Role.*;
import static org.junit.jupiter.api.Assertions.*;
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
    void updateMemberFromMemberDto() {
//        memberMapper.updateMemberFromMemberDto();
    }

    @Test
    void stringToClub() {
    }

    @Test
    void memberToMemberDto() {
    }

    @Test
    void avatarToString() {
    }

    @Test
    void clubToString() {
    }
}