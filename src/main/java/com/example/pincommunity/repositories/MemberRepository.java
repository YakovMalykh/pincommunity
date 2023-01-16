package com.example.pincommunity.repositories;

import com.example.pincommunity.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> getMemberByUsernameIgnoreCase(String email);

    Optional<Member> getMemberById (Long id);
}
