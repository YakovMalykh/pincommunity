package com.example.pincommunity.servicies;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.MemberDto;
import com.example.pincommunity.exceptions.MemberNotFoundException;
import com.example.pincommunity.mappers.MemberMapper;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import com.example.pincommunity.servicies.inpl.AvatarServiceImpl;
import com.example.pincommunity.servicies.inpl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberMapper memberMapper;
    @Mock
    AvatarServiceImpl avatarService;
    @InjectMocks
    MemberServiceImpl memberService;

    @Test
    void createMember() {
        when(memberMapper.createMemberDtoToMember(any(CreateMemberDto.class))).thenReturn(MEMBER);
        when(memberRepository.save(any(Member.class))).thenReturn(MEMBER);
        boolean response = memberService.createMember(CREATE_MEMBER_DTO);
        assertTrue(response);
    }

    @Test
    void isMemberExists_ifMemberExists() {
        when(memberRepository.getMemberByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(MEMBER));
        boolean response = memberService.isMemberExists(EMAIL);
        assertTrue(response);
    }

    @Test
    void isMemberExists_ifMemberDontExist() {
        when(memberRepository.getMemberByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());
        boolean response = memberService.isMemberExists(EMAIL);
        assertFalse(response);
    }

    @Test
    void updateAvatar() {
        MEMBER.setAvatar(AVATAR);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(MEMBER));
        when(avatarService.updateImage(any(Avatar.class), any(MultipartFile.class))).thenReturn(AVATAR);

        ResponseEntity<Void> response = memberService.updateAvatar(1L, FILE);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void updateAvatar_shouldThrowMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        MemberNotFoundException exception = assertThrows(
                MemberNotFoundException.class, () -> memberService.updateAvatar(1L, FILE));
        assertEquals("Member doesn't exist", exception.getMessage());
    }

    @Test
    void updateMember_successful() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(MEMBER));
        doNothing().when(memberMapper).updateMemberFromMemberDto(MEMBER_DTO, MEMBER);
        when(memberRepository.save(any(Member.class))).thenReturn(MEMBER);
        when(memberMapper.memberToMemberDto(any(Member.class))).thenReturn(MEMBER_DTO);

        ResponseEntity<MemberDto> response = memberService.updateMember(1L, MEMBER_DTO);
        assertEquals(ResponseEntity.ok(MEMBER_DTO), response);
    }

    @Test
    void updateMember_shouldThrowMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        MemberNotFoundException exception = assertThrows(
                MemberNotFoundException.class, () -> memberService.updateMember(1L, MEMBER_DTO));
        assertEquals("Member doesn't exist", exception.getMessage());

    }

    @Test
    void getMemberMe_whenSuccessful() {
        when(memberRepository.getMemberByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(MEMBER));
        when(memberMapper.memberToMemberDto(MEMBER)).thenReturn(MEMBER_DTO);

        ResponseEntity<MemberDto> response = memberService.getMemberMe(AUTHENTICATION);

        assertEquals(ResponseEntity.ok(MEMBER_DTO), response);
    }

    @Test
    void getMemberMe_shouldThrowsMemberNotFoundException() {
        when(memberRepository.getMemberByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        MemberNotFoundException exception = assertThrows(
                MemberNotFoundException.class, () -> memberService.getMemberMe(AUTHENTICATION));

        assertEquals("Member wasn't found", exception.getMessage());
    }
}