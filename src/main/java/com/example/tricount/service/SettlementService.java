package com.example.tricount.service;

import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;

public interface SettlementService {

    Settlement create(String username, String title);

    void join(Settlement settlement, Member member);

}
