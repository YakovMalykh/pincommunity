package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.models.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class MemberMapper {

    @Mapping(target = "username",source = "email")
    public abstract Member createMemberDtoToMember(CreateMemberDto createMemberDto);
}
