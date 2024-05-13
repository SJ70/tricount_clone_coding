package com.example.tricount.dto;

import com.example.tricount.entity.Balance;
import java.math.BigDecimal;
import java.util.Collection;

public record BalanceInfoDTO(
        Long balanceId,
        SettlementInfoDTO settlement,
        Collection<AmountPerMemberInfoDTO> amountPerMembers,
        BigDecimal averageAmount,
        Collection<TransferInfoDTO> transfers
) {

    public BalanceInfoDTO(Balance balance) {
        this(
                balance.getId(),
                new SettlementInfoDTO(balance.getSettlement()),
                balance.getAmountPerMembers().stream().map(AmountPerMemberInfoDTO::new).toList(),
                balance.getAverageAmount(),
                balance.getTransfers().stream().map(TransferInfoDTO::new).toList()
        );
    }

}
