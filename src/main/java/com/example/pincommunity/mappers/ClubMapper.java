package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.exceptions.MemberNotFoundException;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class ClubMapper {
    @Autowired
    protected MemberRepository memberRepository;

    @Mapping(target = "admin", source = "adminUsername")
    public abstract Club createClubDtoToClub(CreateClubDto createClubDto);

    public Member stringToMember(String admin) {
        return memberRepository.getMemberByUsernameIgnoreCase(admin).orElseThrow(() ->
                new MemberNotFoundException("Member with username: " + admin + " doesn't exist. See ClubMapper.class, stringToMember method")
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "admin", source = "adminUsername")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateClubFromClubDto(ClubDto clubDto, @MappingTarget Club club);

    public String memberToString(Member member) {
        return member.getUsername();
    }

    @Mapping(target = "adminUsername", source = "admin")
    public abstract ClubDto clubToClubDto(Club club);

}