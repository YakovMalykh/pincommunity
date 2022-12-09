package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.MemberDto;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.ClubRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class MemberMapper {
    @Autowired
    protected ClubRepository clubRepository;

    @Mapping(target = "username", source = "email")
    public abstract Member createMemberDtoToMember(CreateMemberDto createMemberDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "currentClub",source = "clubCity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateMemberFromMemberDto(MemberDto memberDto, @MappingTarget Member member);

    public Club stringToClub(String clubCity) {
        return clubRepository.findByCityIgnoreCase(clubCity).orElseThrow(() ->
                new ClubNotFoundException("Club with city: " + clubCity + " doesn't exist. See MemberMapper.class, stringToClub method"));
    }

    @Mapping(target = "email",source = "username")
    @Mapping(target = "avatarUrl",source = "avatar")
    @Mapping(target = "clubCity",source = "currentClub")
    public abstract MemberDto memberToMemberDto(Member member);
    public String avatarToString(Avatar avatar) {
        return avatar.getAvatarUrl();
    }
    public String clubToString(Club club) {
        return club.getCity();
    }
}
