package com.example.tricount.service;

import com.example.tricount.entity.Balance;
import com.example.tricount.entity.Settlement;
import com.example.tricount.repository.BalanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final SettlementService settlementService;
    private final TransferService transferService;
    private final AmountPerMemberService amountPerMemberService;

    public Balance findById(Long id) {
        return balanceRepository.findById(id).orElseThrow(() -> new RuntimeException("not found balance, id = " + id));
    }

    @Transactional
    public Balance create(Settlement settlement) {
        Balance balance = new Balance(settlement);
        settlement.setBalance(balance);

        balanceRepository.save(balance);
        amountPerMemberService.saveAll(balance.getAmountPerMembers());
        transferService.saveAll(balance.getTransfers());
        settlementService.save(settlement);
        return balance;
    }

}
