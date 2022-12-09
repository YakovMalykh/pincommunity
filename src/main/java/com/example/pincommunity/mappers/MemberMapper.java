package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.models.Member;
import org.mapstruct.*;

@Mapper
public abstract class MemberMapper {

    @Mapping(target = "username",source = "email")
    public abstract Member createMemberDtoToMember(CreateMemberDto createMemberDto);
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "avatar", ignore = true)
//    @Mapping(target = "username",ignore = true)
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    public abstract void updateMemberFromMemberDto(MemberDto memberDto, @MappingTarget Member member);
}
