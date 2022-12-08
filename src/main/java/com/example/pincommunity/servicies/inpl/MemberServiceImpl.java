package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.mappers.MemberMapper;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import com.example.pincommunity.servicies.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public boolean createMember(CreateMemberDto createMemberDto) {
        Member member = memberMapper.createMemberDtoToMember(createMemberDto);
        Member savedMember = memberRepository.save(member);
        log.info("Member with id: " + savedMember.getId() + " and username: " + savedMember.getUsername() + " is saved");
        return true;
    }

    @Override
    public boolean isMemberExists(String email) {
        Optional<Member> optionalMember = memberRepository.getMemberByUsernameIgnoreCase(email);
        return optionalMember.isPresent();
    }

}
