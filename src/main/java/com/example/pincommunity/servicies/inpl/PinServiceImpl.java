package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.*;
import com.example.pincommunity.exceptions.MemberNotFoundException;
import com.example.pincommunity.exceptions.PinNotFoundException;
import com.example.pincommunity.mappers.PinMapper;
import com.example.pincommunity.models.Member;
import com.example.pincommunity.models.Picture;
import com.example.pincommunity.models.Pin;
import com.example.pincommunity.repositories.MemberRepository;
import com.example.pincommunity.repositories.PinRepository;
import com.example.pincommunity.servicies.ImageService;
import com.example.pincommunity.servicies.PinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PinServiceImpl implements PinService {
    private final MemberRepository memberRepository;
    private final PinRepository pinRepository;
    private final PinMapper pinMapper;

    @Qualifier("PictureServiceImpl")
    private final ImageService<Picture> pictureService;

    public PinServiceImpl(PinRepository pinRepository, PinMapper pinMapper, ImageService<Picture> pictureService,
                          MemberRepository memberRepository) {
        this.pinRepository = pinRepository;
        this.pinMapper = pinMapper;
        this.pictureService = pictureService;
        this.memberRepository = memberRepository;
    }

    @Override

    public ResponseEntity<PinDto> createPin(CreatePinDto createPinDto, MultipartFile file, Authentication auth) {
        log.info("PinServiceImpl.createPin");
        Member holder = memberRepository.getMemberByUsernameIgnoreCase(auth.getName()).orElseThrow(() -> {
            log.info("Member with name : " + auth.getName() + " doesn't exist. See PinServiceImpl.class, createPin method");
            throw new MemberNotFoundException("Member with name : " + auth.getName() + " doesn't exist.");
        });
        Pin pin = pinMapper.createPinDtoToPin(createPinDto);
        pin.setHolder(holder);
        Picture savedPicture = pictureService.saveImage(file, pin.getPinsName(), new Picture());
        pin.setPicture(savedPicture);
        Pin savedPin = pinRepository.save(pin);
        PinDto pinDto = pinMapper.pinToPinDto(savedPin);
        return ResponseEntity.ok(pinDto);
    }

    @Override
    public ResponseEntity<ResponseWrapperPinDto> getAllPins() {
        List<Pin> pinList = pinRepository.findAll();
        ResponseWrapperPinDto responseWrapperPinDto = new ResponseWrapperPinDto();
        List<PinDto> pinDtoList = pinMapper.listPinToListPinDto(pinList);
        responseWrapperPinDto.setCount((long) pinDtoList.size());
        responseWrapperPinDto.setResult(pinDtoList);
        log.info("получили все объявления " + responseWrapperPinDto);
        return ResponseEntity.ok(responseWrapperPinDto);
    }

    @Override
    public ResponseEntity<PinDto> getPinById(Long id) {
        Pin pin = pinRepository.findById(id).orElseThrow(() -> {
            log.info("Pin by id " + id + " not found. PinServiceImpl, method getPinById");
            throw new PinNotFoundException("Pin by id " + id + " not found");
        });
        PinDto pinDto = pinMapper.pinToPinDto(pin);
        return ResponseEntity.ok(pinDto);
    }

    @Override
    public ResponseEntity<PinDto> removePinById(Long id) {
        Pin pin = pinRepository.findById(id).orElseThrow(() -> {
            log.info("Pin by id " + id + " not found. PinServiceImpl, method updatePinset");
            throw new PinNotFoundException("Pin by id " + id + " not found");
        });
        pictureService.deleteImageById(pin.getPicture().getId());
        pinRepository.deleteById(id);
        log.info("Pin with Id: " + id + "is deleted");
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<PinDto> updatePin(Long id, CreatePinDto createPinDto) {
        Pin pin = pinRepository.findById(id).orElseThrow(() -> {
            log.info("Pin by id " + id + " not found. PinServiceImpl, method updatePinset");
            throw new PinNotFoundException("Pin by id " + id + " not found");
        });
        pinMapper.updatePinFromCreatePinDto(createPinDto, pin);
        Pin savedPin = pinRepository.save(pin);
        PinDto pinDto = pinMapper.pinToPinDto(savedPin);
        return ResponseEntity.ok(pinDto);
    }

    @Override
    public ResponseEntity<Void> updatePictureOfPin(Long id, MultipartFile file) {
        Pin pin = pinRepository.findById(id).orElseThrow(() -> {
            log.info("Pin by id " + id + " not found. PinServiceImpl, method updatePictureOfPin");
            throw new PinNotFoundException("Pin by id " + id + " not found");
        });
        pictureService.updateImage(pin.getPicture(), file);
        return ResponseEntity.ok().build();
    }
}
