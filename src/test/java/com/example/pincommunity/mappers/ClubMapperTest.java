package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClubMapperTest {
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    ClubMapper clubMapper = Mappers.getMapper(ClubMapper.class);

    @BeforeEach
    void setUp() {
        MEMBER.setId(1L);
        MEMBER.setUsername(EMAIL);

        CREATE_CLUB_DTO.setCity(CLUB_CITY);
        CREATE_CLUB_DTO.setAdminUsername(EMAIL);

        CLUB.setAdmin(MEMBER);
        CLUB.setCity(CLUB_CITY);


    }

    @Test
    void stringToMember() {
        when(memberRepository.getMemberByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(MEMBER));
        Member member = clubMapper.stringToMember(EMAIL);
        assertEquals(EMAIL, member.getUsername());
    }

    @Test
    void memberToString() {
        String email = clubMapper.memberToString(MEMBER);
        assertEquals(EMAIL, email);
    }

    @Test
    void clubToClubDto() {
        ClubDto clubDto = clubMapper.clubToClubDto(CLUB);
        assertEquals(CLUB.getAdmin().getUsername(), clubDto.getAdminUsername());
        assertEquals(CLUB.getCity(), clubDto.getCity());
    }

    @Test
    void createClubDtoToClub() {
        when(memberRepository.getMemberByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(MEMBER));
        Club club = clubMapper.createClubDtoToClub(CREATE_CLUB_DTO);
        assertEquals(CREATE_CLUB_DTO.getAdminUsername(), club.getAdmin().getUsername());
        assertEquals(CREATE_CLUB_DTO.getCity(), club.getCity());
    }
    @Test
    void updateClubFromClubDto(){
        when(memberRepository.getMemberByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(MEMBER));

        Club club=new Club();
        club.setCity("Town");
        CLUB_DTO.setCity(CLUB_CITY);
        CLUB_DTO.setAdminUsername(EMAIL);
        CLUB_DTO.setId(1L);

        clubMapper.updateClubFromClubDto(CLUB_DTO,club);
        assertEquals(CLUB_DTO.getCity(), club.getCity() );
        assertEquals(CLUB_DTO.getAdminUsername(), club.getAdmin().getUsername() );

    }
}
