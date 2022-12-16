package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.MemberDto;
import com.example.pincommunity.exceptions.MemberNotFoundException;
import com.example.pincommunity.mappers.MemberMapper;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import com.example.pincommunity.servicies.ImageService;
import com.example.pincommunity.servicies.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
//@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    @Qualifier("AvatarServiceImpl")
    private final ImageService<Avatar> avatarService;

    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper, ImageService<Avatar> avatarService) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.avatarService = avatarService;
    }


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

    @Override
    public ResponseEntity<MemberDto> updateMember(Long id, MemberDto memberDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> {
            log.info("Member doesn't exist. MemberServiceImpl.class, method updateMember");
            throw new MemberNotFoundException("Member doesn't exist");
        });
        memberMapper.updateMemberFromMemberDto(memberDto, member);
        Member updatedMember = memberRepository.save(member);
        MemberDto updatedMemberDto = memberMapper.memberToMemberDto(updatedMember);
        log.info("Member " + updatedMemberDto.getEmail() + " has been gotten");
        return ResponseEntity.ok(updatedMemberDto);
    }

    @Override
    public ResponseEntity<Void> updateAvatar(Long id, MultipartFile file) {
        Member member = memberRepository.findById(id).orElseThrow(() -> {
            log.info("Member doesn't exist. MemberServiceImpl.class, method updateAvatar");
            throw new MemberNotFoundException("Member doesn't exist");
        });
        if (member.getAvatar() == null) {
            log.info("Member " + member.getId() + " don't have any avatar");
            Avatar avatar = new Avatar();
            Avatar savedAvatar = avatarService.saveImage(file, member.getFullName(), avatar);
            member.setAvatar(savedAvatar);
            memberRepository.save(member);
            log.info("new Avatar is installed");
            return ResponseEntity.ok().build();
        } else {
            Avatar updatedAvatar = avatarService.updateImage(member.getAvatar(), file);
            member.setAvatar(updatedAvatar);
            log.info("Avatar has been update");
            return ResponseEntity.ok().build();
        }
    }

    @Override
    public ResponseEntity<MemberDto> getMemberMe(Authentication authentication) {
        Member member = memberRepository.getMemberByUsernameIgnoreCase(authentication.getName()).orElseThrow(() -> {
            log.info("Member wasn't found. MemberServiceImpl.class, method getMemberMe");
            throw new MemberNotFoundException("Member wasn't found");
        });
        MemberDto memberDto = memberMapper.memberToMemberDto(member);
        log.info("Member " + authentication.getName() + " has been gotten");
        return ResponseEntity.ok(memberDto);
    }

}
