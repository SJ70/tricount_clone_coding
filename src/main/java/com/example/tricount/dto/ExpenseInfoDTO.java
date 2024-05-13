package com.example.tricount.dto;

import com.example.tricount.entity.Expense;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseInfoDTO(
        Long id,
        String title,
        MemberProfileDTO member,
        BigDecimal amount,
        LocalDateTime date
) {

    public ExpenseInfoDTO(Expense expense) {
        this(
                expense.getId(),
                expense.getTitle(),
                new MemberProfileDTO(expense.getMember()),
                expense.getAmount(),
                expense.getDate()
        );
    }

}
