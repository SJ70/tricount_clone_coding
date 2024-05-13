package com.example.tricount.controller;

import com.example.tricount.dto.TransferInfoDTO;
import com.example.tricount.entity.Transfer;
import com.example.tricount.service.MemberService;
import com.example.tricount.service.TransferService;
import com.example.tricount.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final MemberService memberService;

    @Operation(summary = "해당 사용자가 송금해야 하는 정보 모두 조회")
    @GetMapping("/send")
    public ResponseEntity<Collection<TransferInfoDTO>> findRequireToSendByUsername() {
        String username = SecurityUtil.getCurrentUsername();
        Long userId = memberService.findByUsername(username).getId();
        Collection<Transfer> transfers = transferService.findAllBySender(userId);
        return ResponseEntity.ok(transfers.stream().map(TransferInfoDTO::new).toList());
    }

    @Operation(summary = "해당 사용자가 송금받아야 하는 정보 모두 조회")
    @GetMapping("/receive")
    public ResponseEntity<Collection<TransferInfoDTO>> findRequireToReceiveByUsername() {
        String username = SecurityUtil.getCurrentUsername();
        Long userId = memberService.findByUsername(username).getId();
        Collection<Transfer> transfers = transferService.findAllByReceiver(userId);
        return ResponseEntity.ok(transfers.stream().map(TransferInfoDTO::new).toList());
    }

}
