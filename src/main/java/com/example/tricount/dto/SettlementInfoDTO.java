package com.example.tricount.dto;

import com.example.tricount.entity.Settlement;
import java.util.Collection;

public record SettlementInfoDTO(
        Long id,
        String title,
        Collection<MemberProfileDTO> members,
        Collection<ExpenseInfoDTO> expenses
) {

    public SettlementInfoDTO(Settlement settlement) {
        this(
                settlement.getId(),
                settlement.getTitle(),
                settlement.getMembers().stream().map(MemberProfileDTO::new).toList(),
                settlement.getExpenses().stream().map(ExpenseInfoDTO::new).toList()
        );
    }

}
