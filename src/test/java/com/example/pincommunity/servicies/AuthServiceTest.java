package com.example.pincommunity.servicies;

import com.example.pincommunity.servicies.inpl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    void login() {

    }

    @Test
    void registration() {
    }
}
