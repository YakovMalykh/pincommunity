package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.CreatePinsetDto;
import com.example.pincommunity.dto.PinsetDto;
import com.example.pincommunity.dto.ResponseWrapperPinDto;
import com.example.pincommunity.exceptions.PinsetNotFoundException;
import com.example.pincommunity.mappers.PinsetMapper;
import com.example.pincommunity.models.Picture;
import com.example.pincommunity.models.Pin;
import com.example.pincommunity.models.Pinset;
import com.example.pincommunity.repositories.PinRepository;
import com.example.pincommunity.repositories.PinsetRepository;
import com.example.pincommunity.servicies.ImageService;
import com.example.pincommunity.servicies.PinsetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
//@RequiredArgsConstructor
public class PinsetServiceImpl implements PinsetService {

    private final PinsetRepository pinsetRepository;
    private final PinRepository repoOfPins;
    private final PinsetMapper pinsetMapper;
    @Qualifier("PictureServiceImpl")
    private final ImageService<Picture> pictureService;

    public PinsetServiceImpl(PinsetRepository pinsetRepository, PinRepository repoOfPins, PinsetMapper pinsetMapper, ImageService<Picture> pictureService) {
        this.pinsetRepository = pinsetRepository;
        this.repoOfPins = repoOfPins;
        this.pinsetMapper = pinsetMapper;
        this.pictureService = pictureService;
    }

    @Override

    public ResponseEntity<PinsetDto> createPinset(CreatePinsetDto createPinsetDto, MultipartFile file) {
        Pinset pinset = pinsetMapper.createPinsetDtoToPinset(createPinsetDto);
        Picture savedPicture = pictureService.saveImage(file, pinset.getPinsetName(), new Picture());
        pinset.setPinsetPictureId(savedPicture);
        Pinset savedPinset = pinsetRepository.save(pinset);
        PinsetDto pinsetDto = pinsetMapper.pinsetToPinsetDto(savedPinset);
        return ResponseEntity.ok(pinsetDto);
    }

    @Override
    public ResponseEntity<PinsetDto> getPinsetById(Long id) {
        Pinset pinset = pinsetRepository.findById(id).orElseThrow(() ->
                new PinsetNotFoundException("Pinset by id " + id + " not found. PinsetServiceImpl, method getPinsetById"));
        PinsetDto pinsetDto = pinsetMapper.pinsetToPinsetDto(pinset);
        return ResponseEntity.ok(pinsetDto);
    }

    @Override
    public ResponseEntity<PinsetDto> updatePinset(Long id, CreatePinsetDto createPinsetDto) {
        Pinset pinset = pinsetRepository.findById(id).orElseThrow(() ->
                new PinsetNotFoundException("Pinset by id " + id + " not found. PinsetServiceImpl, method updatePinset"));
        pinsetMapper.updatePinsetFromCreatePinsetDto(createPinsetDto, pinset);
        Pinset savedPinset = pinsetRepository.save(pinset);
        PinsetDto pinsetDto = pinsetMapper.pinsetToPinsetDto(savedPinset);
        return ResponseEntity.ok(pinsetDto);
    }

    @Override
    public ResponseEntity<Void> updatePictureOfPinset(Long id, MultipartFile file) {
        Pinset pinset = pinsetRepository.findById(id).orElseThrow(() ->
                new PinsetNotFoundException("Pinset by id " + id + " not found. PinsetServiceImpl, method updatePictureOfPinset"));
        pictureService.updateImage(pinset.getPinsetPictureId(), file);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removePinset(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseWrapperPinDto> getAllPinsOfThisPinset(Long pinsetId) {
        Pinset pinset = pinsetRepository.findById(pinsetId).orElseThrow(() ->
                new PinsetNotFoundException("Pinset by id " + pinsetId + " not found. PinsetServiceImpl, method getAllPinsOfThisPinset"));
        List<Pin> associatedPins = pinset.getAssociatedPins();
        return null;
    }
}
