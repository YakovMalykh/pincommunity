package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.constants.Role;
import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.exceptions.ClubAlreadyExists;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.mappers.ClubMapper;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.ClubRepository;
import com.example.pincommunity.repositories.MemberRepository;
import com.example.pincommunity.servicies.ClubService;
import com.example.pincommunity.servicies.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@Service
//@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubMapper clubMapper;
    @Qualifier("AvatarServiceImpl")
    private final ImageService<Avatar> avatarService;

    public ClubServiceImpl(ClubRepository clubRepository, MemberRepository memberRepository, ClubMapper clubMapper, ImageService<Avatar> avatarService) {
        this.clubRepository = clubRepository;
        this.memberRepository = memberRepository;
        this.clubMapper = clubMapper;
        this.avatarService = avatarService;
    }

    @Override
    public ResponseEntity<ClubDto> createClub(CreateClubDto createClubDto) {
        if (clubRepository.findByCityIgnoreCase(createClubDto.getCity()).isPresent()) {
            log.info("club already exists. ClubServiceImpl, method createClub");
            throw new ClubAlreadyExists("club already exists");
        }
        Club club = clubMapper.createClubDtoToClub(createClubDto);
        Club savedClub = clubRepository.save(club);
        Member member = savedClub.getAdmin();
        setClubAndAdminRoleForMemberAndSaveInDbAfterThat(savedClub, member);
        ClubDto clubDto = clubMapper.clubToClubDto(savedClub);
        return ResponseEntity.ok(clubDto);
    }

    private void setClubAndAdminRoleForMemberAndSaveInDbAfterThat(Club club, Member member) {
        member.setCurrentClub(club);
        member.setRole(Role.ADMIN.name());
        log.info("for " + member.getUsername() + " was set role ADMIN and currentClub");
        memberRepository.save(member);
    }


    @Override
    public ResponseEntity<ClubDto> updateClub(Long id, ClubDto clubDto) {
        Club club = clubRepository.findById(id).orElseThrow(() -> {
            log.info("Club doesn't exists. ClubServiceImpl, method updateClub");
            throw new ClubNotFoundException("Club doesn't exists");
        });
        Member adminBeforeChanges = club.getAdmin();
        changeRoleOfMember(adminBeforeChanges);

        clubMapper.updateClubFromClubDto(clubDto, club);
        Club savedClub = clubRepository.save(club);
        Member member = savedClub.getAdmin();
        setClubAndAdminRoleForMemberAndSaveInDbAfterThat(savedClub, member);
        ClubDto updatedClubDto = clubMapper.clubToClubDto(savedClub);
        log.info("Admin of club: " + club.getCity() + " was changed. New admin: " + member.getUsername());
        return ResponseEntity.ok(updatedClubDto);
    }

    private void changeRoleOfMember(Member member) {
        member.setRole(Role.USER.name());
        log.info("member: " + member.getUsername() + " was set role USER");
        memberRepository.save(member);
    }

    @Override
    public ResponseEntity<Void> updateClubAvatar(Long id, MultipartFile file) {
        Club club = clubRepository.findById(id).orElseThrow(() -> {
            log.info("Club wasn't found. ClubServiceImpl, method updateClubAvatar");
            throw new ClubNotFoundException("Club wasn't found.");
        });
        if (club.getClubAvatar() == null) {
            log.info("Club " + club.getId() + " don't have any avatar");
            Avatar avatar = new Avatar();
            Avatar savedAvatar = avatarService.saveImage(file, club.getCity(), avatar);
            club.setClubAvatar(savedAvatar);
            clubRepository.save(club);
            log.info("new Avatar is installed to Club: " + club.getCity());
            return ResponseEntity.ok().build();
        } else {
            avatarService.updateImage(club.getClubAvatar(), file);
            log.info("Avatar of club: " + club.getCity() + ", has been update");
            return ResponseEntity.ok().build();
        }
    }

    @Override
    public ResponseEntity<ClubDto> getClubById(Long id) {
        Club club = clubRepository.findById(id).orElseThrow(() -> {
            log.info("Club doesn't exist. ClubServiceImpl, method getClubById");
            throw new ClubNotFoundException("Club doesn't exist");
        });
        ClubDto clubDto = clubMapper.clubToClubDto(club);
        log.info("get club " + clubDto.getCity());
        return ResponseEntity.ok(clubDto);
    }
}
