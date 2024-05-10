package com.example.tricount.controller;

import com.example.tricount.dto.CreateSettlementRequestDTO;
import com.example.tricount.entity.Settlement;
import com.example.tricount.service.SettlementService;
import com.example.tricount.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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
        String username = SecurityUtil.getCurrentUsername();
        return settlementService.create(username, requestDTO.title());
    }

}
