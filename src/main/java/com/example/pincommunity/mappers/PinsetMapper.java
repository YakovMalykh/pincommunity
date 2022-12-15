package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.CreatePinsetDto;
import com.example.pincommunity.dto.PinsetDto;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Picture;
import com.example.pincommunity.models.Pinset;
import com.example.pincommunity.repositories.ClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper
@RequiredArgsConstructor
public abstract class PinsetMapper {

    protected ClubRepository clubRepository;

    /**
     * @throws ClubNotFoundException status 404
     */
    @Mapping(target = "originClub", source = "clubCity")
    public abstract Pinset createPinsetDtoToPinset(CreatePinsetDto createPinsetDto);

    public Club stringToClub(String clubCity) {
        return clubRepository.findByCityIgnoreCase(clubCity).orElseThrow(() ->
                new ClubNotFoundException("Club not found in city: " + clubCity + ". PinsetMapper, method stringToClub"));
    }

    @Mapping(target = "pictureUrl", source = "pinsetPictureId")
    @Mapping(target = "clubCity", source = "originClub")
    public abstract PinsetDto pinsetToPinsetDto(Pinset pinset);

    public String pictureToString(Picture picture) {
        return picture.getPictureUrl();
    }

    public String clubToString(Club club) {
        return club.getCity();
    }

    /**
     * @throws ClubNotFoundException status 404
     */
    @Mapping(target = "originClub", source = "clubCity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updatePinsetFromCreatePinsetDto(CreatePinsetDto createPinsetDto, @MappingTarget Pinset pinset);

}


