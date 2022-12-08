package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.servicies.AuthService;
import com.example.pincommunity.servicies.MemberService;
import com.example.pincommunity.servicies.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.pincommunity.constants.Role.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;
    private final PasswordEncoder encoder;
    private final UserService userService;


    @Override
    public boolean login(String email, String password) {
        if (!memberService.isMemberExists(email)) {
            log.info("Member doesn't exist");
            return false;
        } else {
            // нужно проверить, что будет, если мы укажем не существующего пользователя
            UserDetails userDetails = userService.loadUserByUsername(email);
            String encodedPassword = userDetails.getPassword();
            return encoder.matches(password, encodedPassword);
        }
    }

    @Override
    public boolean registration(CreateMemberDto createMemberDto) {
        if (memberService.isMemberExists(createMemberDto.getEmail())) {
            log.info("Member already exists");
            return false;
        }
        String encodedPassword = encoder.encode(createMemberDto.getPassword());
        createMemberDto.setPassword(encodedPassword);
        createMemberDto.setRole(USER);
        return memberService.createMember(createMemberDto);
    }
}
