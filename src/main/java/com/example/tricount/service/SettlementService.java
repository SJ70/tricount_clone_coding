package com.example.tricount.service;

import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import com.example.tricount.repository.MemberRepository;
import com.example.tricount.repository.SettlementRepository;
import jakarta.transaction.Transactional;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    public Settlement findById(Long id) {
        return settlementRepository.findById(id).orElseThrow(() -> new RuntimeException("not existed settlement, id " + id));
    }

    @Transactional
    public Settlement create(String username, String title) {
        Settlement settlement = new Settlement(title);
        Member member = memberService.findByUsername(username);
        this.join(member, settlement); // join()에서 save 하므로 save 생략
        return settlement;
    }

    @Transactional
    public Collection<Member> join(String username, Long settlementId) {
        Member member = memberService.findByUsername(username);
        Settlement settlement = this.findById(settlementId);
        settlement.addMember(member);
        this.join(member, settlement); // join()에서 save 하므로 save 생략
        return settlement.getMembers();
    }

    private void join(Member member, Settlement settlement) {
        member.addSettlement(settlement);
        settlement.addMember(member);
        settlementRepository.save(settlement);
        memberRepository.save(member);
    }


}
