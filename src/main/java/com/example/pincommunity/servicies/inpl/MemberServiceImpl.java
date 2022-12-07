package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import com.example.pincommunity.servicies.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.example.pincommunity.constants.Role.*;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean createMember(CreateMemberDto createMemberDto) {
        Optional<Member> optionalMember = memberRepository.getMemberByUsernameIgnoreCase(createMemberDto.getEmail());
        if (optionalMember.isEmpty()) {
            String encodedPassword = passwordEncoder.encode(createMemberDto.getPassword());
            Member member = new Member();
            member.setUsername(createMemberDto.getEmail());
            member.setPassword(encodedPassword);
            member.setRole(USER.name());
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
