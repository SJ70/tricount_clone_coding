package com.example.tricount.service;

import com.example.tricount.entity.Expense;
import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import com.example.tricount.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MemberService memberService;
    private final SettlementService settlementService;

    @Transactional
    public Expense create(String username, Long settlementId, String title, BigDecimal amount) {
        Member member = memberService.findByUsername(username);
        Settlement settlement = settlementService.findById(settlementId);
        Expense expense = new Expense(title, settlement, member, amount);
        expenseRepository.save(expense);
        return expense;
    }

}
