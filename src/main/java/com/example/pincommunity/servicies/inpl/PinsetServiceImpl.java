package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.CreatePinsetDto;
import com.example.pincommunity.dto.PinDto;
import com.example.pincommunity.dto.PinsetDto;
import com.example.pincommunity.dto.ResponseWrapperPinDto;
import com.example.pincommunity.exceptions.PinsetNotFoundException;
import com.example.pincommunity.mappers.PinMapper;
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

    private final PinMapper pinMapper;
    @Qualifier("PictureServiceImpl")
    private final ImageService<Picture> pictureService;

    public PinsetServiceImpl(PinsetRepository pinsetRepository, PinRepository repoOfPins, PinsetMapper pinsetMapper, PinMapper pinMapper, ImageService<Picture> pictureService) {
        this.pinsetRepository = pinsetRepository;
        this.repoOfPins = repoOfPins;
        this.pinsetMapper = pinsetMapper;
        this.pinMapper = pinMapper;
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
        Pinset pinset = pinsetRepository.findById(id).orElseThrow(() -> {
            log.info("Pinset by id " + id + " not found. PinsetServiceImpl, method getPinsetById");
            throw new PinsetNotFoundException("Pinset by id " + id + " not found");
        });
        PinsetDto pinsetDto = pinsetMapper.pinsetToPinsetDto(pinset);
        return ResponseEntity.ok(pinsetDto);
    }

    @Override
    public ResponseEntity<PinsetDto> updatePinset(Long id, CreatePinsetDto createPinsetDto) {
        Pinset pinset = pinsetRepository.findById(id).orElseThrow(() -> {
            log.info("Pinset by id " + id + " not found. PinsetServiceImpl, method updatePinset");
            throw new PinsetNotFoundException("Pinset by id " + id + " not found");
        });
        pinsetMapper.updatePinsetFromCreatePinsetDto(createPinsetDto, pinset);
        Pinset savedPinset = pinsetRepository.save(pinset);
        PinsetDto pinsetDto = pinsetMapper.pinsetToPinsetDto(savedPinset);
        return ResponseEntity.ok(pinsetDto);
    }

    @Override
    public ResponseEntity<Void> updatePictureOfPinset(Long id, MultipartFile file) {
        Pinset pinset = pinsetRepository.findById(id).orElseThrow(() -> {
            log.info("Pinset by id " + id + " not found. PinsetServiceImpl, method updatePictureOfPinset");
            throw new PinsetNotFoundException("Pinset by id " + id + " not found");
        });
        pictureService.updateImage(pinset.getPinsetPictureId(), file);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removePinset(Long id) {
        Pinset pinset = pinsetRepository.findById(id).orElseThrow(() -> {
            log.info("Pinset by id " + id + " not found. PinsetServiceImpl, method removePinset");
            throw new PinsetNotFoundException("Pinset by id " + id + " not found");
        });
        Picture pinsetPicture = pinset.getPinsetPictureId();
        if (pinsetPicture != null) {
            pictureService.deleteImageById(pinsetPicture.getId());
        }
        List<Pin> associatedPins = pinset.getAssociatedPins();
        if (!associatedPins.isEmpty()) {
            associatedPins.forEach(e -> e.setPinset(null));
        }
        pinsetRepository.delete(pinset);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ResponseWrapperPinDto> getAllPinsOfThisPinset(Long pinsetId) {
        Pinset pinset = pinsetRepository.findById(pinsetId).orElseThrow(() -> {
            log.info("Pinset by id " + pinsetId + " not found. PinsetServiceImpl, method getAllPinsOfThisPinset");
            throw new PinsetNotFoundException("Pinset by id " + pinsetId + " not found");
        });
        List<Pin> associatedPins = pinset.getAssociatedPins();
        List<PinDto> pinDtoList = pinMapper.listPinToListPinDto(associatedPins);
        ResponseWrapperPinDto responseWrapperPinDto = new ResponseWrapperPinDto();
        responseWrapperPinDto.setCount((long) pinDtoList.size());
        responseWrapperPinDto.setResult(pinDtoList);

        return ResponseEntity.ok(responseWrapperPinDto);
    }
}
