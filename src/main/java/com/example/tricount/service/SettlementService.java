package com.example.tricount.service;

import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import java.util.Collection;

public interface SettlementService {

    Settlement findById(Long id);
    Settlement create(String username, String title);
    Collection<Member> join(String username, Long settlementId);

}
