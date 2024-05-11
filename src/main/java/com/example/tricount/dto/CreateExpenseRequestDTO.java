package com.example.tricount.dto;

import java.math.BigDecimal;

public record CreateExpenseRequestDTO(Long settlementId, String title, BigDecimal amount) {

}
