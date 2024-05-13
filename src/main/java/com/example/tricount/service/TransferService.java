package com.example.tricount.service;

import com.example.tricount.entity.Transfer;
import com.example.tricount.repository.TransferRepository;
import jakarta.transaction.Transactional;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    @Transactional
    public void save(Transfer transfer) {
        transferRepository.save(transfer);
    }

    @Transactional
    public void saveAll(Collection<Transfer> transfers) {
        transferRepository.saveAll(transfers);
    }

}
