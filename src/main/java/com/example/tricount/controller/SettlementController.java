package com.example.tricount.controller;

import com.example.tricount.dto.CreateSettlementRequestDTO;
import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import com.example.tricount.service.SettlementService;
import com.example.tricount.util.SecurityUtil;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settlement")
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping()
    public ResponseEntity<Settlement> create(@RequestBody CreateSettlementRequestDTO requestDTO) {
        String username = SecurityUtil.getCurrentUsername();
        Settlement settlement = settlementService.create(username, requestDTO.title());
        return ResponseEntity.status(HttpStatus.CREATED).body(settlement);
    }

    @PostMapping("/join")
    public ResponseEntity<Collection<Member>> join(@RequestParam("id") Long settlementId) {
        String username = SecurityUtil.getCurrentUsername();
        Collection<Member> members = settlementService.join(username, settlementId);
        return ResponseEntity.ok(members);
    }

}
