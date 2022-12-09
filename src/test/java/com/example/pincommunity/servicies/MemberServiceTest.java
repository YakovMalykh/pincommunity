package com.example.pincommunity.servicies;

import com.example.pincommunity.mappers.MemberMapper;
import com.example.pincommunity.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberMapper memberMapper;
    @InjectMocks
    MemberService memberService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createMember() {
    }

    @Test
    void isMemberExists() {
    }

    @Test
    void updateAvatar() {
    }

    @Test
    void updateMember() {
    }

    @Test
    void getMemberMe() {
    }
}