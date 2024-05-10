package com.example.tricount.controller;

import com.example.tricount.dto.CreateSettlementRequestDTO;
import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import com.example.tricount.service.SettlementService;
import com.example.tricount.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settlement")
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping()
    public Settlement create(@RequestBody CreateSettlementRequestDTO requestDTO) {
        String userId = SecurityUtil.getCurrentUserId();
        return settlementService.create(userId, requestDTO.title());
    }

}
