package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.CreatePinDto;
import com.example.pincommunity.dto.PinDto;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.exceptions.PinsetNotFoundException;
import com.example.pincommunity.models.*;
import com.example.pincommunity.repositories.ClubRepository;
import com.example.pincommunity.repositories.MemberRepository;
import com.example.pincommunity.repositories.PinsetRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Mapper
public abstract class PinMapper {
    @Autowired
    protected PinsetRepository pinsetRepository;
    @Autowired
    protected ClubRepository clubRepository;
    @Autowired
    protected MemberRepository memberRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "holder", ignore = true)
    @Mapping(target = "originClub", source = "clubCity")
    @Mapping(target = "pinset", source = "pinsetName")
    public abstract Pin createPinDtoToPin(CreatePinDto createPinDto);

    public Club stringToClub(String clubCity) {
        return clubRepository.findByCityIgnoreCase(clubCity).orElseThrow(() -> {
            log.info("Club for city: " + clubCity + " doesn't exist. See PinMapper.class, stringToClub method");
            throw new ClubNotFoundException("Club for city: " + clubCity + " doesn't exist.");
        });
    }

    public Pinset stringToPinset(String pinsetName) {
        if (pinsetName != null) {
            return pinsetRepository.findByPinsetNameIgnoreCase(pinsetName).orElseThrow(() -> {
                log.info("Pinset with name: " + pinsetName + " doesn't exist. See PinMapper.class, stringToPinset method");
                throw new PinsetNotFoundException("Pinset with name: " + pinsetName + " doesn't exist.");
            });
        } else {
            return null;
        }
    }

    @Mapping(target = "pictureUrl", expression = "java(pin.getPicture().getPictureUrl())")
    @Mapping(target = "clubCity", expression = "java(pin.getOriginClub().getCity())")
    @Mapping(target = "pinsetName",expression = "java(pin.getPinset()==null?null:pin.getPinset().getPinsetName())")
    @Mapping(target = "holdersUsername", expression = "java(pin.getHolder().getUsername())")
    public abstract PinDto pinToPinDto(Pin pin);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "holder", ignore = true)
    @Mapping(target = "originClub", source = "clubCity")
    @Mapping(target = "pinset", source = "pinsetName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updatePinFromCreatePinDto(CreatePinDto createPinDto, @MappingTarget Pin pin);

    public abstract List<PinDto> listPinToListPinDto(List<Pin> adsList);

}
