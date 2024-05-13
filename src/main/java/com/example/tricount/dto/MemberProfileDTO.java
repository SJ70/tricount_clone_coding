package com.example.tricount.dto;

import com.example.tricount.entity.Member;

public record MemberProfileDTO(
        Long id,
        String nickname,
        String username
) {

    public MemberProfileDTO(Member member) {
        this(
                member.getId(),
                member.getNickname(),
                member.getUsername()
        );
    }

}
