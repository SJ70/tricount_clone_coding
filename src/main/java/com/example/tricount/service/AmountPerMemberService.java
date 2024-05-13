package com.example.tricount.service;

import com.example.tricount.entity.AmountPerMember;
import com.example.tricount.repository.AmountPerMemberRepository;
import jakarta.transaction.Transactional;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmountPerMemberService {

    private final AmountPerMemberRepository amountPerMemberRepository;

    @Transactional
    public void save(AmountPerMember amountPerMember) {
        amountPerMemberRepository.save(amountPerMember);
    }

    @Transactional
    public void saveAll(Collection<AmountPerMember> amountPerMembers) {
        amountPerMemberRepository.saveAll(amountPerMembers);
    }

}
