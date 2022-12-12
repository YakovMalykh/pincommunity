package com.example.pincommunity.servicies;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.servicies.inpl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    MemberService memberService;
    @Mock
    PasswordEncoder encoder;
    @Mock
    UserService userService;
    @InjectMocks
    AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void login_whenMemberDoesntExist() {
        when(memberService.isMemberExists(anyString())).thenReturn(false);
        boolean response = authService.login(EMAIL, PASSWORD);
        assertFalse(response);
    }
    @Test
    void login_whenWrongPassword() {
        when(memberService.isMemberExists(anyString())).thenReturn(true);
        when(userService.loadUserByUsername(anyString())).thenReturn(USER_DETAILS);
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        boolean response = authService.login(EMAIL, PASSWORD);
        assertFalse(response);
    }
    @Test
    void login_whenSuccessful() {
        when(memberService.isMemberExists(anyString())).thenReturn(true);
        when(userService.loadUserByUsername(anyString())).thenReturn(USER_DETAILS);
        when(encoder.matches(anyString(), anyString())).thenReturn(true);

        boolean response = authService.login(EMAIL, PASSWORD);
        assertTrue(response);
    }

    @Test
    void registration_whenMemberAlreadyExists_thenShouldReturnFalse() {
        CREATE_MEMBER_DTO.setEmail(EMAIL);
        when(memberService.isMemberExists(anyString())).thenReturn(true);

        boolean response = authService.registration(CREATE_MEMBER_DTO);

        assertFalse(response);
    }

    @Test
    void registration_whenSuccessful() {
        CREATE_MEMBER_DTO.setEmail(EMAIL);
        CREATE_MEMBER_DTO.setPassword(PASSWORD);
        when(memberService.isMemberExists(anyString())).thenReturn(false);
        when(encoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(memberService.createMember(any(CreateMemberDto.class))).thenReturn(true);
        boolean response = authService.registration(CREATE_MEMBER_DTO);

        assertTrue(response);
        assertEquals(ENCODED_PASSWORD, CREATE_MEMBER_DTO.getPassword());
    }


}
