package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.MemberDto;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.ClubRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
@Mapper
public abstract class MemberMapper {
    @Autowired
    protected ClubRepository clubRepository;

    @Mapping(target = "username", source = "email")
    public abstract Member createMemberDtoToMember(CreateMemberDto createMemberDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "currentClub", source = "clubCity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateMemberFromMemberDto(MemberDto memberDto, @MappingTarget Member member);

    public Club stringToClub(String clubCity) {
        return clubRepository.findByCityIgnoreCase(clubCity).orElseThrow(() -> {
            log.info("Club with city: " + clubCity + " doesn't exist. See MemberMapper.class, stringToClub method");
            throw new ClubNotFoundException("Club with city: " + clubCity + " doesn't exist");
        });
    }

    @Mapping(target = "email", source = "username")
    @Mapping(target = "avatarUrl", source = "avatar")
    @Mapping(target = "clubCity", source = "currentClub")
    public abstract MemberDto memberToMemberDto(Member member);

    public String avatarToString(Avatar avatar) {
        if (avatar != null) {
            return avatar.getAvatarUrl();
        }
        return null;
    }

    public String clubToString(Club club) {
        if (club != null) {
            return club.getCity();
        }
        return null;
    }
}
