package com.example.pincommunity.repositories;

import com.example.pincommunity.models.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {
}
