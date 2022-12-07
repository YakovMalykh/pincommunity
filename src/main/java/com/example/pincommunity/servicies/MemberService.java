package com.example.pincommunity.servicies;


import com.example.pincommunity.dto.CreateMemberDto;

public interface MemberService {
    boolean createMember(CreateMemberDto createMemberDto);
}
