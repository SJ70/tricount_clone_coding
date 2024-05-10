package com.example.tricount.service;

import com.example.tricount.dto.CreateMemberRequestDTO;
import com.example.tricount.entity.Member;
import com.example.tricount.jwt.JwtToken;

public interface MemberService {

    Member join(CreateMemberRequestDTO requestDTO);

    JwtToken signIn(String userId, String password);

}
