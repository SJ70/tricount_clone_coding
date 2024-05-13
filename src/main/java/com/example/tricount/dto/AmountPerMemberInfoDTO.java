package com.example.tricount.dto;

import com.example.tricount.entity.AmountPerMember;
import java.math.BigDecimal;

public record AmountPerMemberInfoDTO(String username, String nickname, BigDecimal amount) {

    public AmountPerMemberInfoDTO(AmountPerMember amountPerMember) {
        this(
                amountPerMember.getMember().getUsername(),
                amountPerMember.getMember().getNickname(),
                amountPerMember.getAmount()
        );
    }

}
