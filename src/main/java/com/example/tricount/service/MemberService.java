package com.example.tricount.service;

import com.example.tricount.entity.Member;
import com.example.tricount.jwt.JwtToken;

public interface MemberService {

    Member findByUsername(String username);

    Member join(String nickname, String username, String password);

    JwtToken signIn(String username, String password);

}
