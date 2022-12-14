package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.exceptions.MemberNotFoundException;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Mapper
public abstract class ClubMapper {
    @Autowired
    protected MemberRepository memberRepository;

    /**
     * @throws MemberNotFoundException status 404
     */
    @Mapping(target = "admin", source = "adminUsername")
    public abstract Club createClubDtoToClub(CreateClubDto createClubDto);

    public Member stringToMember(String admin) {
        return memberRepository.getMemberByUsernameIgnoreCase(admin).orElseThrow(() ->
                new MemberNotFoundException("Member with username: " + admin + " doesn't exist. See ClubMapper.class, stringToMember method")
        );

    }

    /**
     * @throws MemberNotFoundException status 404
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "clubAvatar", ignore = true)
    @Mapping(target = "admin", source = "adminUsername")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateClubFromClubDto(ClubDto clubDto, @MappingTarget Club club);

    public String memberToString(Member member) {
        return member.getUsername();
    }

    @Mapping(target = "adminUsername", source = "admin")
    @Mapping(target = "clubAvatarUrl", source = "clubAvatar")
    public abstract ClubDto clubToClubDto(Club club);

    public String avatarToString(Avatar avatar) {
        if (avatar != null) {
            return avatar.getAvatarUrl();
        }
        return null;
    }

}
