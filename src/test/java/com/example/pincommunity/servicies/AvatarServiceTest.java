package com.example.pincommunity.servicies;

import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.repositories.AvatarRepository;
import com.example.pincommunity.servicies.inpl.AvatarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {
    @Mock
    AvatarRepository avatarRepository;

    @InjectMocks
    AvatarServiceImpl avatarService;

    @Test
    void getImageById_whenAvatarExists() {
        when(avatarRepository.findById(anyLong())).thenReturn(Optional.of(AVATAR));

        ResponseEntity<Avatar> response = avatarService.getImageById(1L);

        assertEquals(ResponseEntity.ok(AVATAR), response);
    }

    @Test
    void getImageById_whenAvatarDoesntExists_shouldReturnDefaultAvatar() {
        when(avatarRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Avatar> response = avatarService.getImageById(1L);

        assertEquals("avatars/logo.jpg", Objects.requireNonNull(response.getBody()).getFilePathInFolder());
        assertEquals("image/png", Objects.requireNonNull(response.getBody()).getMediaType());
    }

}