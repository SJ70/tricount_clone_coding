package com.example.tricount.controller;

import com.example.tricount.dto.CreateExpenseRequestDTO;
import com.example.tricount.dto.ExpenseInfoDTO;
import com.example.tricount.entity.Expense;
import com.example.tricount.service.ExpenseService;
import com.example.tricount.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(summary = "지출 생성")
    @PostMapping
    public ResponseEntity<ExpenseInfoDTO> create(CreateExpenseRequestDTO requestDTO) {
        String username = SecurityUtil.getCurrentUsername();
        Expense expense = expenseService.create(username, requestDTO.settlementId(), requestDTO.title(), requestDTO.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ExpenseInfoDTO(expense));
    }

    @Operation(summary = "지출 삭제")
    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestParam("id") Long expenseId) {
        String username = SecurityUtil.getCurrentUsername();
        Expense expense = expenseService.findById(expenseId);
        if (expense.canAccessedBy(username)) {
            expenseService.delete(expense);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

}
