package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.exceptions.PictureNotFoundException;
import com.example.pincommunity.models.Picture;
import com.example.pincommunity.repositories.PictureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PictureServiceImplTest {
    @Mock
    PictureRepository pictureRepository;
    @InjectMocks
    PictureServiceImpl pictureService;

    @Test
    void getImageById_whenPictureExists() {
        when(pictureRepository.findById(anyLong())).thenReturn(Optional.of(PICTURE));

        ResponseEntity<Picture> response = pictureService.getImageById(anyLong());

        assertEquals(ResponseEntity.ok(PICTURE), response);
    }

    @Test
    void getImageById_whenPictureDoesntExists_thenShouldThrowsPictureNotFoundException() {
        when(pictureRepository.findById(anyLong())).thenReturn(Optional.empty());

        PictureNotFoundException exception = assertThrows(PictureNotFoundException.class, () -> pictureService.getImageById(anyLong()));

        assertEquals("Picture doesn't exist", exception.getMessage());
    }
}