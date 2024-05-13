package com.example.tricount.dto;

import com.example.tricount.entity.Transfer;
import java.math.BigDecimal;

public record TransferInfoDTO(
        String senderUsername,
        String senderNickname,
        String receiverUsername,
        String receiverNickname,
        BigDecimal amount
) {

    public TransferInfoDTO(Transfer transfer) {
        this(
                transfer.getSender().getUsername(),
                transfer.getSender().getNickname(),
                transfer.getReceiver().getUsername(),
                transfer.getReceiver().getNickname(),
                transfer.getAmount()
        );
    }

}
