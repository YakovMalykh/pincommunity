package com.example.pincommunity.servicies;


import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    boolean createMember(CreateMemberDto createMemberDto);

    boolean isMemberExists(String email);

    ResponseEntity<Void> updateAvatar(Long id, MultipartFile file);

    ResponseEntity<MemberDto> updateMember(Long id, MemberDto memberDto);
}
