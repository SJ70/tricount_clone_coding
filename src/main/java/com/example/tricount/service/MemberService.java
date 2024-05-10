package com.example.tricount.service;

import com.example.tricount.entity.Member;
import com.example.tricount.jwt.JwtToken;

public interface MemberService {

    Member findByUserId(String userId);

    Member join(String name, String id, String password);

    JwtToken signIn(String userId, String password);

}
