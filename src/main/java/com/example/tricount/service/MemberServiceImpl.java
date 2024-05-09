package com.example.tricount.service;

import com.example.tricount.dto.CreateMemberRequestDTO;
import com.example.tricount.entity.Member;
import com.example.tricount.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Member join(CreateMemberRequestDTO requestDTO) {
        Member member = new Member(
                requestDTO.name(),
                requestDTO.id(),
                requestDTO.password()
        );
        return memberRepository.save(member);
    }

}
