package com.example.pincommunity.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "avatars")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_path_in_folder")
    private String filePathInFolder;
    @Column(name = "media_type")
    private String mediaType;
    @Lob
    private byte[] preview;
}
