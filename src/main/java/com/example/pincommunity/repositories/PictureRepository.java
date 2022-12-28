package com.example.pincommunity.repositories;

import com.example.pincommunity.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
void deleteByPictureUrl(String url);
Optional<Picture> getByPictureUrl (String url);
}
