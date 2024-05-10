package com.example.tricount.service;

import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import com.example.tricount.repository.MemberRepository;
import com.example.tricount.repository.SettlementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository settlementRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Settlement create(String userId, String title) {
        Settlement settlement = new Settlement(title);
        Member member = memberService.findByUserId(userId);
        this.join(settlement, member); // join()에서 save 하므로 save 생략
        return settlement;
    }

    @Override
    @Transactional
    public void join(Settlement settlement, Member member) {
        settlement.addMember(member);
        member.addSettlement(settlement);
        settlementRepository.save(settlement);
        memberRepository.save(member);
    }


}
