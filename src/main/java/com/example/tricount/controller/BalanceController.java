package com.example.tricount.controller;

import com.example.tricount.entity.Balance;
import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import com.example.tricount.service.BalanceService;
import com.example.tricount.service.MemberService;
import com.example.tricount.service.SettlementService;
import com.example.tricount.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;
    private final SettlementService settlementService;

    @Operation(summary = "정산 결과 집계")
    @GetMapping("/calc")
    public ResponseEntity<Balance> calculate(@RequestParam("settlementId") Long settlementId) {
        String username = SecurityUtil.getCurrentUsername();
        Settlement settlement = settlementService.findById(settlementId);

        log.info("집계 시도, username: {}, settlementId: {}", username, settlementId);
        if (!settlement.getMembers().stream().map(Member::getUsername).toList().contains(username)) {
            log.info("집계 권한 없음");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        if (settlement.isBalanced()) {
            log.info("이미 집계 완료된 정산");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Balance balance = balanceService.create(settlement);
        log.info("집계 성공: {}", balance);
        return ResponseEntity.status(HttpStatus.CREATED).body(balance);
    }

}
