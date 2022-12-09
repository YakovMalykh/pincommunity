package com.example.pincommunity.servicies;

import com.example.pincommunity.dto.CreateMemberDto;

public interface AuthService {
    boolean login(String email, String password);

    boolean registration(CreateMemberDto createMemberDto);
}
