package com.example.tricount.service;

import com.example.tricount.dto.CreateMemberRequestDTO;
import com.example.tricount.entity.Member;

public interface MemberService {

    Member join(CreateMemberRequestDTO requestDTO);

}
